package com.leondev.movie_app.di

import com.leondev.movie_app.data.repository.MovieRepositoryImpl
import com.leondev.movie_app.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}