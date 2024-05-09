package com.appsrui.movies.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.appsrui.movies.R
import com.appsrui.movies.common.UiEvents
import com.appsrui.movies.presentation.components.ErrorComponent
import com.appsrui.movies.presentation.components.LoadingComponent
import com.appsrui.movies.presentation.home.movies
import com.appsrui.movies.presentation.theme.MoviesTheme
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieDetailsViewModel: MovieDetailsViewModel = viewModel(),
    movieId: Int,
    navigateToHomeScreen: () -> Unit,
    navigateToMovieDetailScreen: (String) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        movieDetailsViewModel.getMovieDetails(movieId)
        movieDetailsViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    val movieDetailsUiState = movieDetailsViewModel.movieDetailsUiState.collectAsState().value

    MoviesTheme {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF080C2C))
            ) {

                if (movieDetailsUiState.isLoading) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        LoadingComponent()
                    }
                }

                if (!movieDetailsUiState.isLoading && movieDetailsUiState.errorMessage.isNotEmpty()) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        ErrorComponent(errorMessage = movieDetailsUiState.errorMessage)
                    }
                }

                if (!movieDetailsUiState.isLoading && movieDetailsUiState.movieDetails != null) {
                    LazyColumn {
                        item {
                            AsyncImage(
                                model = movieDetailsUiState.movieDetails.moviePosterUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.movie_placeholder),
                                modifier = Modifier.height(300.dp),
                            )

                            IconButton(
                                onClick = { navigateToHomeScreen() },
                                modifier = Modifier.align(Alignment.TopStart),
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFF9E22),
                                    )

                                    Text(
                                        text = movieDetailsUiState.movieDetails.averageVote.toString(),
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Divider(
                                        modifier = Modifier
                                            .height(12.dp)
                                            .width(1.dp),
                                        color = Color.LightGray,
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(
                                        text = movieDetailsUiState.movieDetails.totalVotes.toString(),
                                        color = Color.LightGray,
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = movieDetailsUiState.movieDetails.title,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = movieDetailsUiState.movieDetails.genres,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = movieDetailsUiState.movieDetails.year,
                                    color = Color.LightGray,
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = movieDetailsUiState.movieDetails.overview,
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Similar Movies",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                items(movieDetailsUiState.similarMovies) { movie ->
                                    Column(
                                        modifier = Modifier.clickable {
                                            navigateToMovieDetailScreen(movie.id.toString())
                                        }
                                    ) {
                                        AsyncImage(
                                            model = movie.moviePosterUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            placeholder = painterResource(id = R.drawable.movie_placeholder),
                                            modifier = Modifier
                                                .height(300.dp)
                                                .clip(
                                                    RoundedCornerShape(14.dp),
                                                ),
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = movie.title ?: "---",
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                        )
                                        if (movie.averageVote != null) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                modifier = Modifier.padding(
                                                    top = 4.dp,
                                                    bottom = 20.dp,
                                                ),
                                            ) {
                                                Text(
                                                    text = "${movie.averageVote}",
                                                    color = Color.White
                                                )

                                                Icon(
                                                    imageVector = Icons.Filled.Star,
                                                    contentDescription = null,
                                                    tint = Color(0xFFFF9E22),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}