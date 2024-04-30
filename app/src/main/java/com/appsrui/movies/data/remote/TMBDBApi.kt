package com.appsrui.movies.data.remote

import retrofit2.Response

interface TMBDBApi {
    fun getPopularMovies(): Response<MoviesResponse>

    fun searchMovie(): Response<MoviesResponse>

    fun getMovieDetails(): Response<MoviesResponse.MovieDetails>

    fun getSimilarMovies(): Response<MoviesResponse>
}
