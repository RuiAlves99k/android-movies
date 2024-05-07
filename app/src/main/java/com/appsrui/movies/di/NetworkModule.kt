package com.appsrui.movies.di

import com.appsrui.movies.BuildConfig
import com.appsrui.movies.data.remote.KinocheckApi
import com.appsrui.movies.data.remote.TMDBApi
import com.appsrui.movies.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_TMDB_NAME = "API_TMDB"
    private const val API_KINOCHECK_NAME = "API_KINOCHECK"

    @Provides
    @Singleton
    @Named(API_TMDB_NAME)
    fun provideRetrofitTMDB(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named(API_KINOCHECK_NAME)
    fun provideRetrofitKinocheck(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_TRAILER)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(@Named(API_TMDB_NAME) retrofit: Retrofit): TMDBApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideKinocheckApi(@Named(API_KINOCHECK_NAME) retrofit: Retrofit): KinocheckApi {
        return retrofit.create()
    }
}