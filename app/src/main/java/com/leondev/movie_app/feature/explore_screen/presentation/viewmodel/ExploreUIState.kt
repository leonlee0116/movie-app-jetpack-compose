package com.leondev.movie_app.feature.explore_screen.presentation.viewmodel

import com.leondev.movie_app.feature.movie_list.domain.model.Movie

data class ExploreUIState(
    var isLoading: Boolean = false,
    var currentPage: Int = 1,
    var exploreMovieList: List<Movie> = listOf()
)