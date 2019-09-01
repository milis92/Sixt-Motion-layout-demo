package io.milis.sixt.core.dagger

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import dagger.BindsInstance
import dagger.Component
import io.milis.sixt.core.common.worker.SixtWorkerFactory
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.dagger.providers.CoreModule
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Main
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.milis.sixt.core.domain.services.workers.DataSyncWorker
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@ApplicationScope
@Component(
        modules = [
            CoreModule::class
        ]
)
abstract class CoreComponent : BaseComponent<Application> {

    @Named(Io)
    abstract fun schedulerIo(): Scheduler

    @Named(Main)
    abstract fun schedulerMain(): Scheduler

    abstract fun carsRepository(): CarsRepository

    abstract fun sharedPreferences(): SharedPreferences

    internal abstract fun workerFactory(): SixtWorkerFactory

    internal abstract fun workManager(): WorkManager

    @Component.Factory
    interface Factory {
        fun create(
                @BindsInstance context: Context
        ): CoreComponent
    }
}

interface BaseComponent<T> {

    fun inject(target: T)
}

/**
 * Base dagger component for use in activities.
 */
interface BaseActivityComponent<T : Activity> : BaseComponent<T>

/**
 * Base dagger components for use in services.
 */
interface BaseServiceComponent<T : Service> : BaseComponent<T>

