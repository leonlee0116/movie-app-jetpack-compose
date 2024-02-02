package com.leondev.movie_app.feature.movie_list.presentation.viewmodel

sealed interface MovieListUIEvent {
    data class Paginate(val category: String) : MovieListUIEvent
}