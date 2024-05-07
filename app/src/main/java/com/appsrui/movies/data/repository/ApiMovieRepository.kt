package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.Resource.Error
import com.appsrui.movies.common.UNKNOWN_ERROR_OCCURRED
import com.appsrui.movies.data.remote.MovieResponse
import com.appsrui.movies.data.remote.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiMovieRepository @Inject constructor(private val TMDBApi: TMDBApi) : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MovieResponse>> = flow {
        val response = TMDBApi.getPopularMovies()

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED
            emit(Resource.Error(message = errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchMovie(name: String): Flow<Resource<MovieResponse>> {
        TODO("Not yet implemented")
    }

    override fun getMoviesDetails(movieId: Int): Flow<Resource<MovieResponse.MovieDetails>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMovies(movieId: Int): Flow<Resource<MovieResponse>> {
        TODO("Not yet implemented")
    }

}