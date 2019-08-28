package io.milis.sixt.core.dagger.providers

import android.app.Application
import androidx.room.Room
import io.milis.sixt.core.domain.services.local.Database
import io.milis.sixt.core.domain.services.remote.RemoteService
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig
import io.milis.sixt.core.domain.services.daos.CarDao
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

    @Provides
    fun provideCarDao(database: Database): CarDao = database.cars()
}