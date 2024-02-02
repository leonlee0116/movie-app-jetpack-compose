package com.leondev.movie_app.feature.explore_screen.data.remote

import com.leondev.movie_app.feature.movie_list.data.remote.dto.MovieListDTO
import com.leondev.movie_app.util.constant.BaseApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExploreApi {
    @GET("movie/{category}?language=en-US&")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BaseApi.API_KEY
    ): MovieListDTO
}