package com.appsrui.movies.data.remote

import com.appsrui.movies.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Response<MovieResponse>

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") movieName: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieResponse>

    fun getMovieDetails(): Response<MovieResponse.MovieDetails>

    fun getSimilarMovies(): Response<MovieResponse>
}
