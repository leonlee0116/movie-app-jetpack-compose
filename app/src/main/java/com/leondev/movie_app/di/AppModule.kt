package com.leondev.movie_app.di

import android.app.Application
import androidx.room.Room
import com.leondev.movie_app.feature.explore_screen.data.remote.ExploreApi
import com.leondev.movie_app.feature.movie_detail.data.remote.MovieDetailApi
import com.leondev.movie_app.feature.movie_list.data.local.MovieDatabase
import com.leondev.movie_app.feature.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.feature.movie_video.data.remote.MovieVideoApi
import com.leondev.movie_app.util.constant.BaseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideMovieListApi(): MovieListApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieListApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieVideoApi(): MovieVideoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieVideoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDetailApi(): MovieDetailApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieDetailApi::class.java)
    }

    @Singleton
    @Provides
    fun provideExploreMovieApi(): ExploreApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseApi.BASE_URL)
            .client(client)
            .build()
            .create(ExploreApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }
}