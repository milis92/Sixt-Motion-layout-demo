package io.milis.sixt.core.domain.repositories

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.milis.sixt.core.domain.services.daos.CarDao
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.core.domain.services.remote.RemoteService
import io.milis.sixt.core.domain.services.workers.DataSyncWorker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CarsRepository @Inject constructor(private val remoteService: RemoteService,
                                         private val carsDao: CarDao,
                                         workManager: WorkManager) {

    private companion object {
        const val SYNC_JOB_NAME = "sync_job"
    }

    init {
        workManager.enqueueUniquePeriodicWork(
                SYNC_JOB_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<DataSyncWorker>(4, TimeUnit.HOURS).build()
        )
    }

    @CheckReturnValue
    fun getCars(): Flowable<List<Car>> = carsDao.getAll()

    @CheckReturnValue
    fun filter(query: String) = carsDao.filter(query)

}