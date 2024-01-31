package com.leondev.movie_app.movie_video.data.remote

import com.leondev.movie_app.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.movie_video.data.remote.dto.MovieVideoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVideoApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "49ae2a29a5baca80e76d821f5273db65"
    }

    @GET("movie/{movie_id}/videos?language=en-US")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = MovieListApi.API_KEY
    ): MovieVideoDTO
}