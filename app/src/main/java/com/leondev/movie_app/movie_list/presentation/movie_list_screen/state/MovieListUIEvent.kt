package com.leondev.movie_app.movie_list.presentation.movie_list_screen.state

sealed interface MovieListUIEvent {
    data class Paginate(val category: String) : MovieListUIEvent
    data class Navigate(val page: String) : MovieListUIEvent
}