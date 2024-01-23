package com.leondev.movie_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leondev.movie_app.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDao
}