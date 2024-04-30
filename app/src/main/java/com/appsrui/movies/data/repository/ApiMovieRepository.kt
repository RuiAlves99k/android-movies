package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.data.remote.MoviesResponse
import kotlinx.coroutines.flow.Flow

class ApiMovieRepository : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun searchMovie(name: String): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun getMoviesDetails(movieId: Int): Flow<Resource<MoviesResponse.MovieDetails>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMovies(movieId: Int): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

}
