package com.appsrui.movies.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsrui.movies.common.NOT_AVAILABLE
import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.SOMETHING_WENT_WRONG
import com.appsrui.movies.common.UNKNOWN
import com.appsrui.movies.common.UiEvents
import com.appsrui.movies.common.roundOffDecimal
import com.appsrui.movies.data.remote.MovieResponse
import com.appsrui.movies.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _movieDetailsUiState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()


    private fun setUiStateIsLoading() {
        viewModelScope.launch {
            _movieDetailsUiState.value = _movieDetailsUiState.value.copy(
                isLoading = true
            )
        }
    }

    private fun handleMovieDetails(movieDetails: MovieResponse.MovieDetails?): MovieDetailsUi {
        return MovieDetailsUi(
            id = movieDetails?.id,
            title = movieDetails?.title ?: UNKNOWN,
            averageVote = movieDetails?.averageVote?.roundOffDecimal(),
            moviePosterUrl = "https://image.tmdb.org/t/p/w220_and_h330_face/${movieDetails?.moviePosterUrl}",
            totalVotes = movieDetails?.totalVotes,
            year = movieDetails?.year ?: NOT_AVAILABLE,
            overview = movieDetails?.overview ?: UNKNOWN,
            genres = movieDetails?.genres?.map { it.name }?.joinToString(", ") ?: UNKNOWN,
        )
    }

    private fun updateMovieDetailsUiState(movieDetailsUi: MovieDetailsUi) {
        viewModelScope.launch {
            _movieDetailsUiState.value = _movieDetailsUiState.value.copy(
                movieDetails = movieDetailsUi,
                isLoading = false,
            )
        }
    }

    private fun sendErrorUiState(message: String?) {
        viewModelScope.launch {
            _movieDetailsUiState.value = _movieDetailsUiState.value.copy(
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

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            setUiStateIsLoading()
            moviesRepository.getMoviesDetails(movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val movieDetails = handleMovieDetails(result.data)
                        updateMovieDetailsUiState(movieDetails)
                    }

                    is Resource.Error -> {
                        sendErrorUiState(result.message)
                    }

                    else -> _movieDetailsUiState
                }
            }
        }
    }

    fun getSimilarMovies(movieId: Int) {}
}