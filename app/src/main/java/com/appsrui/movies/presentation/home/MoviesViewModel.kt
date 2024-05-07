package com.appsrui.movies.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.SOMETHING_WENT_WRONG
import com.appsrui.movies.common.UiEvents
import com.appsrui.movies.common.roundOffDecimal
import com.appsrui.movies.data.remote.MovieResponse
import com.appsrui.movies.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState: StateFlow<MoviesUiState> = _moviesUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private fun setUiStateIsLoading() {
        viewModelScope.launch {
            _moviesUiState.value = moviesUiState.value.copy(
                isLoading = true
            )
        }
    }

    private fun handleMovieResponse(movieResponse: MovieResponse?): List<MovieResponse.MovieDetails>? {
        return movieResponse?.results?.map {
            it.copy(
                averageVote = it.averageVote?.roundOffDecimal(),
                moviePosterUrl = "https://image.tmdb.org/t/p/w220_and_h330_face/${it.moviePosterUrl}",
            )
        }
    }

    private fun updateUiStateMovies(movies: List<MovieResponse.MovieDetails>?) {
        viewModelScope.launch {
            _moviesUiState.value = moviesUiState.value.copy(
                isLoading = false,
                movies = movies ?: emptyList()
            )
        }
    }

    private fun sendErrorUiState(message: String?) {
        viewModelScope.launch {
            _moviesUiState.value = _moviesUiState.value.copy(
                isLoading = false,
                errorMessage = message ?: SOMETHING_WENT_WRONG,
            )

            _eventFlow.emit(
                UiEvents.SnackBarEvent(
                    message = message ?: SOMETHING_WENT_WRONG
                )
            )
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            setUiStateIsLoading()
            moviesRepository.getPopularMovies().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val popularMovies = handleMovieResponse(result.data)
                        updateUiStateMovies(popularMovies)
                    }

                    is Resource.Error -> {
                        sendErrorUiState(result.message)
                    }

                    else -> {
                        moviesUiState
                    }
                }
            }
        }
    }

    fun searchMovie() {
        viewModelScope.launch {
            setUiStateIsLoading()
            val typedMovie = moviesUiState.value.typedMovie
            moviesRepository.searchMovie(typedMovie).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val searchedMovies = handleMovieResponse(result.data)
                        updateUiStateMovies(searchedMovies)
                    }

                    is Resource.Error -> {
                        sendErrorUiState(result.message)
                    }

                    else -> {
                        moviesUiState
                    }
                }
            }
        }
    }

    fun setTypedMovie(typedMovie: String) {
        _moviesUiState.value = moviesUiState.value.copy(
            typedMovie = typedMovie
        )
    }
}