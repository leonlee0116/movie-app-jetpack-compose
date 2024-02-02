package com.leondev.movie_app.feature.movie_list.domain.repository

import com.leondev.movie_app.feature.movie_list.domain.model.Movie
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>
}