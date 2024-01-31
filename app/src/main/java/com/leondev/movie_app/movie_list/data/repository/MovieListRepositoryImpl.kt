package com.leondev.movie_app.movie_list.data.repository

import com.leondev.movie_app.movie_list.data.local.MovieDatabase
import com.leondev.movie_app.movie_list.data.mapper.toMovie
import com.leondev.movie_app.movie_list.data.mapper.toMovieEntity
import com.leondev.movie_app.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.movie_list.domain.model.Movie
import com.leondev.movie_app.movie_list.domain.repository.MovieListRepository
import com.leondev.movie_app.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieListApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
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

}