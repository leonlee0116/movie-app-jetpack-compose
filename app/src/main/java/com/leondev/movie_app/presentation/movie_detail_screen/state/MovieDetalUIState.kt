package com.leondev.movie_app.presentation.movie_detail_screen.state

import com.leondev.movie_app.domain.model.MovieDetail

data class MovieDetalUIState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null
)