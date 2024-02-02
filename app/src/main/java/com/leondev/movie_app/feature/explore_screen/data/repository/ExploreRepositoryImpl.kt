package com.leondev.movie_app.feature.explore_screen.data.repository

import com.leondev.movie_app.feature.explore_screen.data.remote.ExploreApi
import com.leondev.movie_app.feature.explore_screen.domain.repository.ExploreRepository
import com.leondev.movie_app.feature.movie_list.data.mapper.toMovie
import com.leondev.movie_app.feature.movie_list.domain.model.Movie
import com.leondev.movie_app.util.enum.MovieCategory
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreApi: ExploreApi
) : ExploreRepository {
    override suspend fun getExploreMovieList(page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteData = try {
                exploreApi.getMovieList(MovieCategory.UPCOMING.category, page)
            } catch (e: Exception) {
                emit(Resource.Loading(false))
                emit(Resource.Error(e.message.toString()))
                return@flow
            }

            val movieList = remoteData.results.map { it.toMovie(MovieCategory.UPCOMING.category) }
            emit(Resource.Loading(false))
            emit(Resource.Success(movieList))
        }
    }
}