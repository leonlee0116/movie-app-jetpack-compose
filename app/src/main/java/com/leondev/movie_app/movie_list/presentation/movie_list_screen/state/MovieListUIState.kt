package com.leondev.movie_app.movie_list.presentation.movie_list_screen.state

import com.leondev.movie_app.movie_list.domain.model.Movie
import com.leondev.movie_app.util.route.Screen

data class MovieListUIState(
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val topRatedMovieListPage: Int = 1,

    val currentPage: String = Screen.MovieList.route,

    val popularMovieList: List<Movie> = emptyList(),
    val topRatedMovieList: List<Movie> = emptyList()
)