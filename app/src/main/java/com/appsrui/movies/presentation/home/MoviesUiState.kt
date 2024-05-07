package com.appsrui.movies.presentation.home

import com.appsrui.movies.data.remote.MovieResponse

/**
 * This function contains all the possible user interface states
 */
data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<MovieResponse.MovieDetails> = emptyList(),
    val errorMessage: String = "",
    val typedMovie: String = "",
)