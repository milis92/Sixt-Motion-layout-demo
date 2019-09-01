package io.milis.core.dagger

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Component
import io.milis.sixt.core.dagger.providers.*
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ApplicationScope
@Component(modules = [
    GsonModule::class,
    RetrofitModule::class,
    OkHttpModule::class,
    TestModule::class
])
abstract class TestComponent {

    abstract fun gson(): Gson
    abstract fun converterFactory(): GsonConverterFactory
    abstract fun retrofit(): Retrofit
    abstract fun loginInterceptor(): HttpLoggingInterceptor
    abstract fun okHttpClientBuilder(): OkHttpClient.Builder
    abstract fun provideDefaultHeaders(): Headers
    abstract fun okHttpClient(): OkHttpClient
    abstract fun sharedPreferences() : SharedPreferences

    @Component.Factory
    interface Factory {
        fun create() : TestComponent
    }
}
