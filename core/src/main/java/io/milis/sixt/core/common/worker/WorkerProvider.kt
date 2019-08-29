package io.milis.sixt.core.common.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

class WorkerProvider {

    interface Factory {
        fun create(context: Context, params: WorkerParameters): ListenableWorker
    }

}