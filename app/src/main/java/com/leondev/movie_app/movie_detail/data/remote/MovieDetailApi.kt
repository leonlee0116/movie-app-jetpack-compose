package com.leondev.movie_app.movie_detail.data.remote

import com.leondev.movie_app.movie_detail.data.remote.dto.MovieDetailDTO
import com.leondev.movie_app.movie_list.data.remote.MovieListApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = MovieListApi.API_KEY
    ): MovieDetailDTO
}