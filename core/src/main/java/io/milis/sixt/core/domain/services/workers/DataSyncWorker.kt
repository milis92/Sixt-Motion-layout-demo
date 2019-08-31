package io.milis.sixt.core.domain.services.workers

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.domain.services.daos.CarDao
import io.milis.sixt.core.domain.services.remote.RemoteService
import io.reactivex.Scheduler
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Named

class DataSyncWorker
@AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val carsDao: CarDao,
        private val remoteService: RemoteService,
        @Named(Io) private val scheduler: Scheduler
) : RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        return remoteService.getCars().doOnSuccess {
            carsDao.sync(it)
        }.map {
            Result.success()
        }
    }

    override fun getBackgroundScheduler(): Scheduler {
        return scheduler
    }

    @AssistedInject.Factory
    interface Factory : WorkerProvider.Factory
}