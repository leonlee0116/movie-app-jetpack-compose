package com.leondev.movie_app.feature.explore_screen.domain.repository

import com.leondev.movie_app.feature.movie_list.domain.model.Movie
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    suspend fun getExploreMovieList(page: Int): Flow<Resource<List<Movie>>>
}