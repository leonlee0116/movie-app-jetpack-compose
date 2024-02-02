package com.leondev.movie_app.feature.explore_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.leondev.movie_app.feature.explore_screen.presentation.viewmodel.ExploreUIState

@Composable
fun ExploreScreen(movieListUIState: ExploreUIState, navController: NavHostController) {
    if (movieListUIState.isLoading) {
        CircularProgressIndicator()
    }

    if (movieListUIState.exploreMovieList.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )
    }
}