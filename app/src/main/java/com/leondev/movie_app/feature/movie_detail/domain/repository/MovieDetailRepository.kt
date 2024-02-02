package com.leondev.movie_app.feature.movie_detail.domain.repository

import com.leondev.movie_app.feature.movie_detail.domain.model.MovieDetail
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>>
}