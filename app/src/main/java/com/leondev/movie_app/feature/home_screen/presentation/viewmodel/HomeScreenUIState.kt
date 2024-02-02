package com.leondev.movie_app.feature.home_screen.presentation.viewmodel

import com.leondev.movie_app.util.route.Screen

data class HomeScreenUIState(
    val currentPage: String = Screen.MovieList.route,
)
