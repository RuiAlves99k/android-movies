package com.appsrui.movies.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KinocheckApi {
    @GET("/movies")
    fun getTrailer(
        @Query("tmdb_id") tmdbId: Int,
    ): Response<TrailerResponse>
}