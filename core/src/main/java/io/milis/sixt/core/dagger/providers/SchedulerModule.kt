package io.milis.sixt.core.dagger.providers

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    companion object {
        const val Io = "io"
        const val Main = "main"
    }

    @Provides
    @Named(Io)
    fun provideIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(Main)
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

}

