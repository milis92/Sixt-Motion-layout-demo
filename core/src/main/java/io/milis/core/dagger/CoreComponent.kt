package io.milis.core.dagger

import android.app.Activity
import android.app.Application
import android.app.Service
import io.milis.core.dagger.providers.CoreModule
import io.milis.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.core.dagger.providers.SchedulerModule.Companion.Main
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.Scheduler
import javax.inject.Named

@Component(
        modules = [
            CoreModule::class
        ]
)
abstract class CoreComponent : AndroidInjector<DaggerApplication> {

    @Named(Io)
    abstract fun schedulerIo(): Scheduler

    @Named(Main)
    abstract fun schedulerMain(): Scheduler

    @Component.Factory
    interface Factory {
        fun create(
                @BindsInstance application: Application
        ): CoreComponent
    }
}

