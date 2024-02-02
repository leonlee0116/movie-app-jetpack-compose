package com.leondev.movie_app.feature.movie_list.presentation.viewmodel

import com.leondev.movie_app.feature.movie_list.domain.model.Movie

data class MovieListUIState(
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val topRatedMovieListPage: Int = 1,

    val popularMovieList: List<Movie> = emptyList(),
    val topRatedMovieList: List<Movie> = emptyList()
)