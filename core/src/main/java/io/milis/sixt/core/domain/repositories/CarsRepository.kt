package io.milis.sixt.core.domain.repositories

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.domain.services.daos.CarDao
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.core.domain.services.workers.DataSyncWorker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ApplicationScope
class CarsRepository @Inject constructor(private val carsDao: CarDao,
                                         private val workManager: WorkManager) : Repository<Car> {


    private companion object {
        const val SYNC_JOB_NAME = "sync_job"
    }

    init {
        sync().subscribe()
    }

    override fun sync(): Completable {
        return Completable.fromCallable {
            workManager.enqueueUniquePeriodicWork(
                    SYNC_JOB_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    PeriodicWorkRequestBuilder<DataSyncWorker>(4, TimeUnit.HOURS).build()
            )
        }
    }

    override fun observe(): Flowable<List<Car>> = carsDao.getAll()

    override fun getAll(): Single<List<Car>> = Single.fromPublisher(carsDao.getAll())

    override fun getSingle(uuid: String): Single<Car> = carsDao.getSingle(uuid)

    override fun insert(vararg values: Car): Completable = Completable.fromCallable { carsDao.insert(*values) }

    @Throws(NotImplementedError::class)
    override fun update(value: Car): Completable = throw NotImplementedError()

    override fun delete(value: Car): Completable = carsDao.delete(value)

    override fun prune(): Completable = Completable.fromCallable { carsDao.delete() }

    @CheckReturnValue
    fun findSimilar(make: String, modelName: String) = carsDao.filter(make, modelName)

}