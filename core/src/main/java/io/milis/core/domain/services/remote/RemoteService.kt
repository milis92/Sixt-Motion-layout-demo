package io.milis.core.domain.services.remote

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteService {

    @POST("/oauth/token")
    fun signIn(@Body hashMap: HashMap<String, Any>): Completable


}