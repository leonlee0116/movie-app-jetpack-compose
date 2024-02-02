package com.leondev.movie_app.feature.movie_video.domain.model

import com.leondev.movie_app.feature.movie_video.data.remote.dto.MovieVideoResultsDTO

data class MovieVideo(
    val id: Int,
    val results: List<MovieVideoResultsDTO>
)
