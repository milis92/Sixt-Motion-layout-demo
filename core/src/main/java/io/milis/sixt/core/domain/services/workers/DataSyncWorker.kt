package io.milis.sixt.core.domain.services.workers

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Named

class DataSyncWorker
@AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val carsRepository: CarsRepository,
        @Named(Io) private val scheduler: Scheduler
) : RxWorker(context, params) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public override fun createWork(): Single<Result> {
        return carsRepository.sync()
                .andThen(Single.just(Result.success()))
    }

    override fun getBackgroundScheduler(): Scheduler {
        return scheduler
    }

    @AssistedInject.Factory
    interface Factory : WorkerProvider.Factory
}