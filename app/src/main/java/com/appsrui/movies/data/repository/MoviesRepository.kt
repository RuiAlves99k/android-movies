package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.data.remote.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

	fun getPopularMovies(): Flow<Resource<MoviesResponse>>

	fun searchMovie(name: String): Flow<Resource<MoviesResponse>>

	fun getMoviesDetails(movieId: Int): Flow<Resource<MoviesResponse.MovieDetails>>

	fun getSimilarMovies(movieId: Int): Flow<Resource<MoviesResponse>>
}
