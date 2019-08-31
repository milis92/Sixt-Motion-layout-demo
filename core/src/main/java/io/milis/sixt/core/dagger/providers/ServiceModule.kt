package io.milis.sixt.core.dagger.providers

import android.content.Context
import androidx.room.Room
import io.milis.sixt.core.domain.services.local.Database
import io.milis.sixt.core.domain.services.remote.CarRemoteService
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.domain.services.daos.CarLocalService
import retrofit2.Retrofit

@Module
internal class ServiceModule {

    @ApplicationScope
    @Provides
    fun provideRemoteService(retrofit: Retrofit): CarRemoteService =
            retrofit.create(CarRemoteService::class.java)

    @ApplicationScope
    @Provides
    fun provideLocalService(context: Context): Database =
            Room.databaseBuilder(
                    context,
                    Database::class.java,
                    BuildConfig.LIBRARY_PACKAGE_NAME
            ).build()

    @ApplicationScope
    @Provides
    fun provideCarDao(database: Database): CarLocalService = database.cars()
}