package io.milis.sixt.core.dagger.providers

import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import dagger.Module
import dagger.Provides
import io.milis.sixt.core.BuildConfig
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
internal class RetrofitModule {

    @ApplicationScope
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,
                        okHttpClient: OkHttpClient,
                        @Named(Io) scheduler: Scheduler): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(gsonConverterFactory)
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler))
                    .validateEagerly(true)
                    .build()
}