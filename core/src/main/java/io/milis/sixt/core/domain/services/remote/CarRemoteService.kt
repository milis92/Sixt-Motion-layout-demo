package io.milis.sixt.core.domain.services.remote

import io.milis.sixt.core.domain.services.entities.Car
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface CarRemoteService {

    @Headers("X-Anonymous: true")
    @GET("/codingtask/cars")
    fun getCars(): Single<List<Car>>


}