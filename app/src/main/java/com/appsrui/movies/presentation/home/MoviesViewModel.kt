package com.appsrui.movies.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.SOMETHING_WENT_WRONG
import com.appsrui.movies.common.UiEvents
import com.appsrui.movies.common.roundOffDecimal
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

    fun getPopularMovies() {
        viewModelScope.launch {
            _moviesUiState.value = moviesUiState.value.copy(
                isLoading = true,
            )
            moviesRepository.getPopularMovies().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val popularMovies = result.data?.results?.map {
                            it.copy(
                                averageVote = it.averageVote?.roundOffDecimal(),
                                moviePosterUrl = "https://image.tmdb.org/t/p/w220_and_h330_face/${it.moviePosterUrl}",
                            )
                        }
                        _moviesUiState.value = moviesUiState.value.copy(
                            isLoading = false,
                            movies = popularMovies ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _moviesUiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message ?: SOMETHING_WENT_WRONG,
                        )

                        _eventFlow.emit(
                            UiEvents.SnackBarEvent(
                                message = result.message ?: SOMETHING_WENT_WRONG
                            )
                        )
                    }
                    else -> {
                        moviesUiState
                    }
                }
            }
        }
    }

    fun searchMovie() {

    }

    fun setTypedMovie(typedMovie: String) {

    }
}