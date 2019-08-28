package ch.bmapp.core.domain.services.remote

import android.content.SharedPreferences
import android.text.TextUtils
import androidx.core.content.edit
import ch.bmapp.core.BuildConfig
import io.milis.core.domain.repositories.AuthRepository.Companion.ACCESS_TOKEN
import io.milis.core.domain.repositories.AuthRepository.Companion.REFRESH_TOKEN
import io.milis.core.domain.repositories.AuthRepository.Companion.TOKEN_REFRESHED_AT
import ch.bmapp.core.domain.services.entities.dtos.AuthDTO
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.concurrent.withLock

class RequestAuthenticator
@Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson,
        private val okHttpClientBuilder: OkHttpClient.Builder,
        private val defaultHeaders: Headers
) : Authenticator {

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private var running: Boolean = false

    override fun authenticate(route: Route?, response: Response): Request? {
        val request: Request?

        if (TextUtils.isEmpty(response.request.header("X-Authorize")) || !
                TextUtils.isEmpty(response.request.header("X-Anonymous"))) {
            return null
        }

        val tokenResponse = refreshAccessToken(System.currentTimeMillis(),
                response.request.url.toString())

        if (tokenResponse == null) {
            return null
        } else {
            request = response.request.newBuilder()
                    .headers(defaultHeaders)
                    .header("X-Authorize", tokenResponse.accessToken)
                    .build()
        }

        return request
    }

    private fun refreshAccessToken(startingAt: Long, previousUrl: String): AuthDTO? {
        lock.withLock {
            Timber.d("Authenticator: Locking Io Thread")

            while (running) {
                condition.await()
            }

            val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)
            val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
            val tokenObtainedAt = sharedPreferences.getLong(TOKEN_REFRESHED_AT, 0)

            if (refreshToken == null || accessToken == null) {
                Timber.d("Authenticator: Refresh token is null, skipping renewal")
                condition.signalAll()
                running = false
                return null
            }


            if (tokenObtainedAt > startingAt) {
                Timber.d("Authenticator: Token already renewed, skipping refresh")
                Timber.d("Authenticator: Unlocking Io Thread")
                condition.signalAll()
                running = false
                return AuthDTO(accessToken, refreshToken)
            }

            val request = Request.Builder()
                    .headers(defaultHeaders)
                    .header("X-Anonymous", "true")
                    .url("${BuildConfig.API_BASE_URL}/api/v1/users/authenticate")
                    .post("".toRequestBody("application/json".toMediaTypeOrNull()))
                    .build()

            Timber.d("Authenticator: Refreshing token")
            running = true
            okHttpClientBuilder
                    .build()
                    .newCall(request)
                    .execute()
                    .use { response ->
                        running = false
                        return if (response.isSuccessful && response.body != null) {
                            val newAccessToken = gson.fromJson(response.body!!.charStream(), AuthDTO::class.java)

                            sharedPreferences.edit(true) {
                                putString(ACCESS_TOKEN, newAccessToken.accessToken)
                                putString(REFRESH_TOKEN, newAccessToken.refreshToken)
                                putLong(TOKEN_REFRESHED_AT, System.currentTimeMillis())
                            }
                            Timber.d("Authenticator: Unlocking Io Thread")
                            Timber.d("Authenticator: Token renewed for  $previousUrl")
                            condition.signalAll()
                            return newAccessToken
                        } else {
                            if (response.code in 400..499) {
                                Timber.d("Authenticator: Received ${response.code} from api, pruning tokens...")
                                sharedPreferences.edit {
                                    remove(ACCESS_TOKEN)
                                    remove(REFRESH_TOKEN)
                                    remove(TOKEN_REFRESHED_AT)
                                }
                            }

                            Timber.d("Authenticator: Unlocking Io Thread")
                            Timber.d("Authenticator: Token renewal failed for $previousUrl")

                            condition.signalAll()
                            null
                        }
                    }
        }
    }
}