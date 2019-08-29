package io.milis.sixt.core.dagger.providers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig

@Module(
        includes = [
            GsonModule::class,
            RetrofitModule::class,
            OkHttpModule::class,
            ServiceModule::class,
            SchedulerModule::class,
            WorkerAssistedModule::class,
            WorkerFactoryModule::class
        ]
)
internal class CoreModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideWorkManager(application: Application): WorkManager = WorkManager.getInstance(application)

}