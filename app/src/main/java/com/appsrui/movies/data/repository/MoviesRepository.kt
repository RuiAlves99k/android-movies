package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.data.remote.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

	fun getPopularMovies(): Flow<Resource<MovieResponse>>

	fun searchMovie(name: String): Flow<Resource<MovieResponse>>

	fun getMoviesDetails(movieId: Int): Flow<Resource<MovieResponse.MovieDetails>>

	fun getSimilarMovies(movieId: Int): Flow<Resource<MovieResponse>>
}
