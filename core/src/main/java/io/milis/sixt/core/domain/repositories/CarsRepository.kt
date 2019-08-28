package io.milis.sixt.core.domain.repositories

import io.milis.sixt.core.domain.services.daos.CarDao
import io.milis.sixt.core.domain.services.remote.RemoteService
import javax.inject.Inject

class CarsRepository @Inject constructor(private val remoteService: RemoteService,
                                         private val carsDao: CarDao) {

}