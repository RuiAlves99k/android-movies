package com.appsrui.movies.di

import com.appsrui.movies.data.remote.KinocheckApi
import com.appsrui.movies.data.remote.TMDBApi
import com.appsrui.movies.data.repository.ApiMovieRepository
import com.appsrui.movies.data.repository.ApiTrailerRepository
import com.appsrui.movies.data.repository.MoviesRepository
import com.appsrui.movies.data.repository.TrailerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(TMDBApi: TMDBApi): MoviesRepository {
        return ApiMovieRepository(TMDBApi)
    }

    @Provides
    @Singleton
    fun provideTrailerRepository(kinocheckApi: KinocheckApi): TrailerRepository {
        return ApiTrailerRepository(kinocheckApi)
    }
}