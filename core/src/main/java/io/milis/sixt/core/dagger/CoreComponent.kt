package io.milis.sixt.core.dagger

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Component
import io.milis.sixt.core.common.worker.SixtWorkerFactory
import io.milis.sixt.core.common.worker.WorkerProvider
import io.milis.sixt.core.dagger.providers.CoreModule
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Main
import io.reactivex.Scheduler
import javax.inject.Named

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

    abstract fun workerFactory() : SixtWorkerFactory

    abstract fun sharedPreferences(): SharedPreferences

    @Component.Factory
    interface Factory {
        fun create(
                @BindsInstance application: Application
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

