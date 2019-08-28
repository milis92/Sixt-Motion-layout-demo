package io.milis.sixt.core.dagger.providers

import io.milis.sixt.core.domain.HeadersInterceptor
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
internal class OkHttpModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

    @Provides
    fun provideOkHttpClientBuilder() =
            OkHttpClient.Builder().apply {
                followRedirects(true)
                followSslRedirects(true)
            }

    @Provides
    fun provideOkHttpClient(
            okHttpClientBuilder: OkHttpClient.Builder,
            loggingInterceptor: HttpLoggingInterceptor,
            requestInterceptor: HeadersInterceptor
    ): OkHttpClient =
            okHttpClientBuilder.apply {
                addInterceptor(requestInterceptor)
                addInterceptor(loggingInterceptor)
            }.build()

    @Provides
    fun provideDefaultHeaders(): Headers = headersOf(
            "Content-Type", "application/json",
            "Accept", "application/json",
            "X-Client-Platform", "android",
            "X-Client-Version", BuildConfig.VERSION_NAME
    )
}