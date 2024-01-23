package com.leondev.movie_app.domain.model

import com.leondev.movie_app.data.remote.dto.Genre
import com.leondev.movie_app.data.remote.dto.ProductionCompany
import com.leondev.movie_app.data.remote.dto.ProductionCountry
import com.leondev.movie_app.data.remote.dto.SpokenLanguage

data class MovieDetail(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
