package io.milis.sixt.core.domain.services.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.domain.services.daos.CarDao

class DataSyncWorker
@AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted private val params: WorkerParameters,
        private val carDao: CarDao
) : Worker(context, params) {

    override fun doWork(): Result {


        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : WorkerProvider.Factory
}