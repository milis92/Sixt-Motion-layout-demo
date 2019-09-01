package io.milis.core.dagger

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class TestModule {
    companion object {
        const val Io = "io"
        const val Main = "main"
    }

    @ApplicationScope
    @Provides
    @Named(Io)
    fun provideIOScheduler(): Scheduler = Schedulers.trampoline()

    @ApplicationScope
    @Provides
    @Named(Main)
    fun provideMainScheduler(): Scheduler = Schedulers.trampoline()

    @Provides
    @ApplicationScope
    fun provideSharedPreferences(): SharedPreferences = mock()
}