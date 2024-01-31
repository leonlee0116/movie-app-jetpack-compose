package com.leondev.movie_app.movie_video.domain.repository

import com.leondev.movie_app.movie_video.domain.model.MovieVideoResults
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieVideoRepository {
    suspend fun getMovieVideo(movieId: Int): Flow<Resource<List<MovieVideoResults>>>
}