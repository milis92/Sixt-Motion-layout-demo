package io.milis.core.dagger.providers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.milis.core.BuildConfig

@Module(
        includes = [
            GsonModule::class,
            RetrofitModule::class,
            OkHttpModule::class,
            ServiceModule::class,
            SchedulerModule::class
        ]
)
internal class CoreModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, Context.MODE_PRIVATE)

}