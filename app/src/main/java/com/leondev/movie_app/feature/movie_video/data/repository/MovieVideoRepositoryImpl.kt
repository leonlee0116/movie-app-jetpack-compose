package com.leondev.movie_app.feature.movie_video.data.repository

import com.leondev.movie_app.feature.movie_video.data.mapper.toMovieVideoResults
import com.leondev.movie_app.feature.movie_video.data.remote.MovieVideoApi
import com.leondev.movie_app.feature.movie_video.domain.model.MovieVideoResults
import com.leondev.movie_app.feature.movie_video.domain.repository.MovieVideoRepository
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieVideoRepositoryImpl @Inject constructor(
    private val movieApi: MovieVideoApi,
) : MovieVideoRepository {
    override suspend fun getMovieVideo(movieId: Int): Flow<Resource<List<MovieVideoResults>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteData = try {
                movieApi.getMovieVideo(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movie:"))
                return@flow
            }

            emit(
                Resource.Success(remoteData.results?.map { it.toMovieVideoResults() })
            )
        }
    }
}