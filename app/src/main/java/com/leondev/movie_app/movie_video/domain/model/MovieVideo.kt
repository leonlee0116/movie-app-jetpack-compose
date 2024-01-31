package com.leondev.movie_app.movie_video.domain.model

import com.leondev.movie_app.movie_video.data.remote.dto.MovieVideoResultsDTO

data class MovieVideo(
    val id: Int,
    val results: List<MovieVideoResultsDTO>
)
