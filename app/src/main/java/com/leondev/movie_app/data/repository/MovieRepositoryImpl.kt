package com.leondev.movie_app.data.repository

import com.leondev.movie_app.data.local.MovieDatabase
import com.leondev.movie_app.data.mapper.toMovie
import com.leondev.movie_app.data.mapper.toMovieDetail
import com.leondev.movie_app.data.mapper.toMovieEntity
import com.leondev.movie_app.data.remote.MovieApi
import com.leondev.movie_app.domain.model.Movie
import com.leondev.movie_app.domain.model.MovieDetail
import com.leondev.movie_app.domain.repository.MovieRepository
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieRepository {
    override suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localData = movieDatabase.movieDAO.getMovieByCategory(category)

            if (localData.isEmpty() && !fetchFromRemote) {
                emit(Resource.Success(localData.map { it.toMovie(category) }))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteData = try {
                movieApi.getMovieList(category, page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movie:"))
                return@flow
            }

            val movieListEntity = remoteData.results.map { it.toMovieEntity(category) }
            movieDatabase.movieDAO.upsertMovieList(movieListEntity)
            emit(Resource.Success(movieListEntity.map { it.toMovie(category) }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteData = try {
                movieApi.getMovieDetail(movieId)
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