package io.milis.sixt.core.domain.repositories

import io.milis.sixt.core.domain.services.daos.CarDao
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.core.domain.services.remote.RemoteService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.CheckReturnValue
import javax.inject.Inject

class CarsRepository @Inject constructor(private val remoteService: RemoteService,
                                         private val carsDao: CarDao) {

    @CheckReturnValue
    fun sync(): Completable =
            remoteService.getCars().doOnSuccess {
                carsDao.sync(it)
            }.ignoreElement()

    @CheckReturnValue
    fun getCars(): Flowable<List<Car>> = carsDao.getAll()

    @CheckReturnValue
    fun filter(query: String) = carsDao.filter(query)

}