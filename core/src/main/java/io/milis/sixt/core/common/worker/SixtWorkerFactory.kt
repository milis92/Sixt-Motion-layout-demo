package io.milis.sixt.core.common.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class SixtWorkerFactory
@Inject constructor(
        private val workerFactories: Map<Class<out ListenableWorker>,
                @JvmSuppressWildcards Provider<WorkerProvider.Factory>>
) : WorkerFactory() {

    override fun createWorker(
            context: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
    ): ListenableWorker {
        val foundEntry = workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
                ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factoryProvider.get().create(context, workerParameters)
    }
}