package com.leondev.movie_app.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.leondev.movie_app.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: String) : MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieByCategory(category: String) : List<MovieEntity>
}