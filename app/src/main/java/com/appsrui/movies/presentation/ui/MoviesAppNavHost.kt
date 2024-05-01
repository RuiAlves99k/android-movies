package com.appsrui.movies.presentation.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.appsrui.movies.presentation.details.MovieDetailScreen
import com.appsrui.movies.presentation.details.MovieDetailsViewModel
import com.appsrui.movies.presentation.home.HomeScreen
import com.appsrui.movies.presentation.home.MoviesViewModel

@Composable
fun MoviesAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "home_screen",
    ) {
        composable(route = "home_screen") {
            val viewModel = hiltViewModel<MoviesViewModel>()
            HomeScreen(
                moviesViewModel = viewModel,
                navigateToDetailsScreen = { id ->
                    navHostController.navigate(
                        route = "detail_screen/$id"
                    )
                }
            )
        }

        composable(
            route = "detail_screen/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            navBackStackEntry.arguments?.getInt("id")?.let {
                MovieDetailScreen(
                    movieId = it,
                    movieDetailsViewModel = viewModel,
                    navigateToHomeScreen = {
                        navHostController.navigate(
                            route = "home_screen"
                        )
                    }
                )
            }
        }
    }
}