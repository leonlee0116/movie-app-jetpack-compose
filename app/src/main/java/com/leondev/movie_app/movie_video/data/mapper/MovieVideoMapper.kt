package com.leondev.movie_app.movie_video.data.mapper

import com.leondev.movie_app.movie_video.data.remote.dto.MovieVideoDTO
import com.leondev.movie_app.movie_video.data.remote.dto.MovieVideoResultsDTO
import com.leondev.movie_app.movie_video.domain.model.MovieVideo
import com.leondev.movie_app.movie_video.domain.model.MovieVideoResults

fun MovieVideoResultsDTO.toMovieVideoResults(): MovieVideoResults {
    return MovieVideoResults(
        id = id ?: "",
        iso_3166_1 = iso_3166_1 ?: "",
        iso_639_1 = iso_639_1 ?: "",
        key = key ?: "",
        name = name ?: "",
        official = official ?: false,
        published_at = published_at ?: "",
        site = site ?: "",
        size = size ?: -1,
        type = type ?: ""
    )
}

fun MovieVideoDTO.toMovieVideo(): MovieVideo {
    return MovieVideo(
        id = id ?: -1,
        results = results ?: listOf()
    )
}
