package com.appsrui.movies.presentation.details

import com.appsrui.movies.data.remote.MovieResponse

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsUi? = null,
    val similarMovies: List<MovieResponse.MovieDetails> = emptyList(),
    val errorMessage: String = "",
)

data class MovieDetailsUi(
    val id: Int?,
    val title: String,
    val averageVote: Double?,
    val moviePosterUrl: String,
    val totalVotes: Int?,
    val year: String,
    val overview: String,
    val genres: String,
)