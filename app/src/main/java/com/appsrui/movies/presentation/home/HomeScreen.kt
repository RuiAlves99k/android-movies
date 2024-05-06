package com.appsrui.movies.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appsrui.movies.common.UiEvents
import com.appsrui.movies.data.remote.MovieResponse
import com.appsrui.movies.presentation.composables.SearchMovieTextField
import com.appsrui.movies.presentation.theme.MoviesTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    moviesViewModel: MoviesViewModel = viewModel(),
    navigateToDetailsScreen: (String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        moviesViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    val moviesUiState = moviesViewModel.moviesUiState.collectAsState().value

    MoviesTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            val spanCount = 2

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF080C2C)),
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(spanCount),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    item(span = {
                        GridItemSpan(spanCount)
                    }) {
                        SearchMovieTextField(
                            setTypedMovie = { typedMovie ->
                                moviesViewModel.setTypedMovie(typedMovie = typedMovie)
                            },
                            typedMovie = moviesUiState.typedMovie,
                            onClearClicked = {
                                moviesViewModel.setTypedMovie("")
                                moviesViewModel.getPopularMovies()
                            },
                            searchMovie = {
                                moviesViewModel.searchMovie()
                            }
                        )
                    }
                }
            }
        }
    }
}

val movies = listOf(
    MovieResponse.MovieDetails(
        id = 1,
        averageVote = 9.0,
        moviePosterUrl = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_Ratio0.6716_AL_.jpg",
        title = "Top Gun Maverick",
        genres = emptyList(),
        totalVotes = 100,
        year = "2023",
        overview = "-----",
    ),
    MovieResponse.MovieDetails(
        id = 1,
        averageVote = 8.0,
        moviePosterUrl = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_Ratio0.6716_AL_.jpg",
        title = "Fast & Furious 22",
        genres = emptyList(),
        totalVotes = 100,
        year = "2023",
        overview = "-----",
    ),
    MovieResponse.MovieDetails(
        id = 1,
        averageVote = 8.2,
        moviePosterUrl = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_Ratio0.6716_AL_.jpg",
        title = "Toy Story",
        genres = emptyList(),
        totalVotes = 100,
        year = "2023",
        overview = "-----",
    ),
    MovieResponse.MovieDetails(
        id = 1,
        averageVote = 7.4,
        moviePosterUrl = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_Ratio0.6716_AL_.jpg",
        title = "Joker 3",
        genres = emptyList(),
        totalVotes = 100,
        year = "2023",
        overview = "-----",
    ),
)