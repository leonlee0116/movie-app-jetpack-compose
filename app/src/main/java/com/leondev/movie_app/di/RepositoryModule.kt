package com.leondev.movie_app.di

import com.leondev.movie_app.movie_detail.data.repository.MovieDetailRepositoryImpl
import com.leondev.movie_app.movie_detail.domain.repository.MovieDetailRepository
import com.leondev.movie_app.movie_list.data.repository.MovieListRepositoryImpl
import com.leondev.movie_app.movie_list.domain.repository.MovieListRepository
import com.leondev.movie_app.movie_video.data.repository.MovieVideoRepositoryImpl
import com.leondev.movie_app.movie_video.domain.repository.MovieVideoRepository
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
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieListRepositoryImpl): MovieListRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailRepository(movieDetailRepository: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    @Singleton
    abstract fun bindMovieVideoRepository(movieVideoRepository: MovieVideoRepositoryImpl): MovieVideoRepository
}