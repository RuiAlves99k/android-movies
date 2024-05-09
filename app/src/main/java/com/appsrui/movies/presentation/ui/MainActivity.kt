package com.appsrui.movies.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.appsrui.movies.common.ConnectivityObserver
import com.appsrui.movies.common.NetworkConnectivityObserver
import com.appsrui.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        super.onCreate(savedInstanceState)
        setContent {
            val status by connectivityObserver.observe().collectAsState(initial = null)
            when (status?.ordinal){
                0, 1 -> Toast.makeText(this, "Network internet: ${status?.name}", Toast.LENGTH_LONG).show()
            }
            MoviesApp()
        }
    }

    @Composable
    fun MoviesApp() {
        MoviesTheme {
            val navHostController = rememberNavController()

            MoviesAppNavHost(navHostController = navHostController)
        }
    }
}