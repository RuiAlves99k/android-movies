package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.data.remote.MovieResponse
import kotlinx.coroutines.flow.Flow

class ApiMovieRepository : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MovieResponse>> {
        TODO("Not yet implemented")
    }

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
