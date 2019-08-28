package io.milis.core.dagger.providers

import ch.bmapp.core.domain.services.remote.RequestAuthenticator
import ch.bmapp.core.domain.services.remote.RequestInterceptor
import dagger.Module
import dagger.Provides
import io.milis.core.BuildConfig
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
            interceptor: HttpLoggingInterceptor,
            authenticator: RequestAuthenticator,
            requestInterceptor: RequestInterceptor
    ): OkHttpClient =
            okHttpClientBuilder.apply {
                authenticator(authenticator)
                addInterceptor(requestInterceptor)
                addInterceptor(interceptor)
            }.build()

    @Provides
    fun provideDefaultHeaders(): Headers = headersOf(
            "Content-Type", "application/json",
            "Accept", "application/json",
            "X-Client-Platform", "android",
            "X-Client-Version", BuildConfig.VERSION_NAME
    )
}