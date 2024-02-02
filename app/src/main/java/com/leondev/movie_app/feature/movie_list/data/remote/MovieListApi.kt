package com.leondev.movie_app.feature.movie_list.data.remote

import com.leondev.movie_app.feature.movie_list.data.remote.dto.MovieListDTO
import com.leondev.movie_app.util.constant.BaseApi.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListApi {
    @GET("movie/{category}?language=en-US&")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieListDTO


    @GET("movie/{movie_id}/similar?language=en-US&")
    suspend fun getSimilarMovieList(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieListDTO


}