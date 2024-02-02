package com.leondev.movie_app.feature.movie_detail.presentation

import com.leondev.movie_app.feature.movie_detail.domain.model.MovieDetail

data class MovieDetailUIState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null
)