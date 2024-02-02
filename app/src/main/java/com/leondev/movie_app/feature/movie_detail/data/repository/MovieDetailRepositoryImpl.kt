package com.leondev.movie_app.feature.movie_detail.data.repository

import com.leondev.movie_app.feature.movie_detail.data.remote.MovieDetailApi
import com.leondev.movie_app.feature.movie_detail.data.remote.dto.toMovieDetail
import com.leondev.movie_app.feature.movie_detail.domain.model.MovieDetail
import com.leondev.movie_app.feature.movie_detail.domain.repository.MovieDetailRepository
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteData = try {
                movieDetailApi.getMovieDetail(movieId)
            } catch (e: Exception) {
                emit(Resource.Error(message = "Error loading movie: ${e.message}"))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Success(remoteData.toMovieDetail()))
            emit(Resource.Loading(false))
        }
    }
}