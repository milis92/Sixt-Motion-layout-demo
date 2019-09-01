package io.milis.sixt.core.dagger.providers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig
import io.milis.sixt.core.dagger.scopes.ApplicationScope

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

    @ApplicationScope
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, Context.MODE_PRIVATE)

    @ApplicationScope
    @Provides
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)

}