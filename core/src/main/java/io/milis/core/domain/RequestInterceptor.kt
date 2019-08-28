package ch.bmapp.core.domain.services.remote

import android.content.SharedPreferences
import android.text.TextUtils
import io.milis.core.domain.repositories.AuthRepository.Companion.ACCESS_TOKEN
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class RequestInterceptor
@Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val defaultHeaders: Headers
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
                .headers(defaultHeaders)
                .build()
        try {
            if (!TextUtils.isEmpty(chain.request().header("X-Anonymous"))) {
                return chain.proceed(request)
            } else {
                val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
                if (accessToken == null) {
                    return chain.proceed(request)
                } else {
                    request = chain.request().newBuilder()
                            .headers(defaultHeaders)
                            .header("X-Authorize", accessToken)
                            .build()
                }
            }
        } catch (exception: Throwable) {
            Timber.e(exception)
        }

        return chain.proceed(request)
    }
}