package com.appsrui.movies.presentation.home

import com.appsrui.movies.data.remote.MoviesResponse

data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<MoviesResponse.MovieDetails>? = null,
    val errorMessage: String? = null,
    val typedMovie: String = "",
)