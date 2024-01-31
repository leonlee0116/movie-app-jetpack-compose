package com.leondev.movie_app.movie_list.data.remote.dto

data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)