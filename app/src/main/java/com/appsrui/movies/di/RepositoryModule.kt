package com.appsrui.movies.di

import com.appsrui.movies.data.remote.TMBDBApi
import com.appsrui.movies.data.repository.ApiMovieRepository
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
    fun provideMoviesRepository(TMDBApi: TMBDBApi): ApiMovieRepository {
        return ApiMovieRepository()
    }
}