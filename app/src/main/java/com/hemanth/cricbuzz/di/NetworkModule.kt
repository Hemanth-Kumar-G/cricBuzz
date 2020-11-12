package com.hemanth.cricbuzz.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.hemanth.cricbuzz.BuildConfig
import com.hemanth.cricbuzz.common.NetworkConnectionInterceptor
import com.hemanth.cricbuzz.data.RepoService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = GsonBuilder().excludeFieldsWithModifiers(
            Modifier.FINAL,
            Modifier.TRANSIENT,
            Modifier.STATIC
        )
        val gson = builder.create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }
}