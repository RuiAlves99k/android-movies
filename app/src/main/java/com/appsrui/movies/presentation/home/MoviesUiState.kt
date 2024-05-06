package com.appsrui.movies.presentation.home

import com.appsrui.movies.data.remote.MovieResponse

data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<MovieResponse.MovieDetails>? = null,
    val errorMessage: String? = null,
    val typedMovie: String = "",
)