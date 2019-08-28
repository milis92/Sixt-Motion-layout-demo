package io.milis.core.dagger.providers

import android.app.Application
import androidx.room.Room
import io.milis.core.domain.services.local.Database
import io.milis.core.domain.services.remote.RemoteService
import dagger.Module
import dagger.Provides
import io.milis.core.BuildConfig
import retrofit2.Retrofit

@Module
internal class ServiceModule {

    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService =
            retrofit.create(RemoteService::class.java)

    @Provides
    fun provideLocalService(application: Application): Database =
            Room.databaseBuilder(
                    application,
                    Database::class.java,
                    BuildConfig.LIBRARY_PACKAGE_NAME
            ).build()
}