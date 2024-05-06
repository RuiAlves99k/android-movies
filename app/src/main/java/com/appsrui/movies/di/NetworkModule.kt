package com.appsrui.movies.di

import com.appsrui.movies.BuildConfig
import com.appsrui.movies.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("API_TMDB")
    fun provideRetrofitTMDB(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("API_KINOCHECK")
    fun provideRetrofitKinocheck(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_TRAILER)
            .build()
    }
}