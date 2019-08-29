package io.milis.sixt.core.dagger.providers

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.dagger.mapkeys.WorkerClass
import io.milis.sixt.core.domain.services.workers.DataSyncWorker

@Module
abstract class WorkerFactoryModule {

    @Binds
    @IntoMap
    @WorkerClass(DataSyncWorker::class)
    internal abstract fun bindDataSyncWorker(factory: DataSyncWorker.Factory): WorkerProvider.Factory

}