package com.appsrui.movies.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {
    @GET("/3/movie/popular")
    fun getPopularMovies(): Response<MovieResponse>

    fun searchMovie(): Response<MovieResponse>

    fun getMovieDetails(): Response<MovieResponse.MovieDetails>

    fun getSimilarMovies(): Response<MovieResponse>
}
