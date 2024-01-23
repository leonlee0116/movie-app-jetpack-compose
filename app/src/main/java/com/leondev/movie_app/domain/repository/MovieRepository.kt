package com.leondev.movie_app.domain.repository

import com.leondev.movie_app.domain.model.Movie
import com.leondev.movie_app.domain.model.MovieDetail
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetail(
        movieId: String
    ): Flow<Resource<MovieDetail>>
}