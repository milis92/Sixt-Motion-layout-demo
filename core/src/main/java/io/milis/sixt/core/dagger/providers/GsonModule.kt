package io.milis.sixt.core.dagger.providers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal class GsonModule {

    @ApplicationScope
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @ApplicationScope
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)
}