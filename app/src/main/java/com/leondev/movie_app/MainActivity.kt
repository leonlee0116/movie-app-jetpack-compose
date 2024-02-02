package com.leondev.movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.leondev.movie_app.feature.home_screen.presentation.HomeScreen
import com.leondev.movie_app.feature.movie_detail.presentation.MovieDetailScreen
import com.leondev.movie_app.feature.movie_detail.presentation.viewmodel.MovieDetailViewModel
import com.leondev.movie_app.feature.movie_video.presentation.MovieVideoScreen
import com.leondev.movie_app.feature.movie_video.presentation.viewmodel.MovieVideoViewModel
import com.leondev.movie_app.util.route.Screen
import com.leondev.movie_app.util.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MovieAppTheme {
                setStatusBarColor()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController)
                        }

                        composable(
                            Screen.Detail.route + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val movieDetailListViewModel = hiltViewModel<MovieDetailViewModel>()
                            val movieDetailUIState by movieDetailListViewModel.movieDetailUIState.collectAsState()
                            MovieDetailScreen(navController, movieDetailUIState)
                        }

                        composable(
                            Screen.Video.route + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val movieVideoViewModel = hiltViewModel<MovieVideoViewModel>()
                            val movieVideoResult by movieVideoViewModel.movieVideoResult.collectAsState()
                            MovieVideoScreen(navController, movieVideoResult)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun setStatusBarColor() {
    val systemUIController = rememberSystemUiController()
    val color = Color.Transparent
    LaunchedEffect(color) {
        systemUIController.setStatusBarColor(color)
    }
}
