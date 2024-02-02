package com.leondev.movie_app.feature.home_screen.presentation.viewmodel

sealed interface HomeScreenUIEvent {
    data class Navigate(val page: String) : HomeScreenUIEvent
}