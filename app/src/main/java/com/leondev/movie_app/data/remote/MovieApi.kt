package com.leondev.movie_app.data.remote

import com.leondev.movie_app.data.remote.dto.MovieDetailDTO
import com.leondev.movie_app.data.remote.dto.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val API_KEY = "49ae2a29a5baca80e76d821f5273db65"
    }

    @GET("movie/{category}?language=en-US&")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieListDTO

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailDTO
}