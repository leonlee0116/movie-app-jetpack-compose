package com.leondev.movie_app.di

import android.app.Application
import androidx.room.Room
import com.leondev.movie_app.movie_detail.data.remote.MovieDetailApi
import com.leondev.movie_app.movie_list.data.local.MovieDatabase
import com.leondev.movie_app.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.movie_video.data.remote.MovieVideoApi
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
            .baseUrl(MovieListApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieListApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieVideoApi(): MovieVideoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieListApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieVideoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDetailApi(): MovieDetailApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieListApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieDetailApi::class.java)
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