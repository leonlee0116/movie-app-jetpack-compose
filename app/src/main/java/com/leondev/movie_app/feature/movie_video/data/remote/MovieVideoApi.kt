package com.leondev.movie_app.feature.movie_video.data.remote

import com.leondev.movie_app.feature.movie_video.data.remote.dto.MovieVideoDTO
import com.leondev.movie_app.util.constant.BaseApi.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVideoApi {
    @GET("movie/{movie_id}/videos?language=en-US")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieVideoDTO
}