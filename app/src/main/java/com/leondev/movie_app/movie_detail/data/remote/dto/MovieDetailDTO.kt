package com.leondev.movie_app.movie_detail.data.remote.dto

import com.leondev.movie_app.movie_detail.domain.model.MovieDetail
import com.leondev.movie_app.movie_list.data.remote.dto.Genre

data class MovieDetailDTO(
    val id: Int?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>?,
    val production_countries: List<ProductionCountry>?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

fun MovieDetailDTO.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id ?: -1,
        adult ?: false,
        backdrop_path ?: "",
        belongs_to_collection ?: "",
        budget ?: -1,
        genres ?: listOf(),
        homepage ?: "",
        imdb_id ?: "",
        original_language ?: "",
        original_title ?: "",
        overview ?: "",
        popularity ?: -1.0,
        poster_path ?: "",
        production_companies ?: listOf(),
        production_countries ?: listOf(),
        release_date ?: "",
        revenue ?: -1,
        runtime ?: -1,
        spoken_languages ?: listOf(),
        status ?: "",
        tagline ?: "",
        title ?: "",
        video ?: false,
        vote_average ?: -1.0,
        vote_count ?: -1
    )
}