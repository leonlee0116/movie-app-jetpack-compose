package com.leondev.movie_app.presentation.movie_detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.leondev.movie_app.presentation.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailScreen(navController: NavHostController) {
    val movieDetailListViewModel = hiltViewModel<MovieDetailViewModel>()
    val movieDetailUIState = movieDetailListViewModel.movieDetailUIState.collectAsState().value

    Box(Modifier.fillMaxSize()) {
        movieDetailUIState.movieDetail?.let {
            Text(text = it.title, fontSize = 50.sp)
        } ?: run {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .fillMaxSize()
            )
        }
    }

}