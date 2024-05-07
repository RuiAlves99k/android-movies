package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.UNKNOWN_ERROR_OCCURRED
import com.appsrui.movies.data.remote.MovieResponse
import com.appsrui.movies.data.remote.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class ApiMovieRepository @Inject constructor(private val TMDBApi: TMDBApi) : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MovieResponse>> = flow {
        val response = TMDBApi.getPopularMovies()
        handleResponse(this, response)
    }.flowOn(Dispatchers.IO)

    override fun searchMovie(name: String): Flow<Resource<MovieResponse>> = flow {
        val response = TMDBApi.searchMovie(name)
        handleResponse(this, response)
    }.flowOn(Dispatchers.IO)

    override fun getMoviesDetails(movieId: Int): Flow<Resource<MovieResponse.MovieDetails>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMovies(movieId: Int): Flow<Resource<MovieResponse>> {
        TODO("Not yet implemented")
    }

    private suspend fun <T> handleResponse(
        flowCollector: FlowCollector<Resource<T>>,
        response: Response<T>
    ) {
        if (response.isSuccessful) {
            flowCollector.emit(Resource.Success(data = response.body()))
        } else {
            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED
            flowCollector.emit(Resource.Error(message = errorMessage))
        }
    }
}