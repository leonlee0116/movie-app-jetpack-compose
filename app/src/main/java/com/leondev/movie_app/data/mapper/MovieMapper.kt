package com.leondev.movie_app.data.mapper

import com.leondev.movie_app.data.local.entity.MovieDetailEntity
import com.leondev.movie_app.data.local.entity.MovieEntity
import com.leondev.movie_app.data.remote.dto.MovieDTO
import com.leondev.movie_app.data.remote.dto.MovieDetailDTO
import com.leondev.movie_app.domain.model.Movie
import com.leondev.movie_app.domain.model.MovieDetail

fun MovieEntity.toMovie(
    category: String?
): Movie {
    return Movie(
        id,
        category ?: "",
        adult,
        backdrop_path,
        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf<Int>()
        },
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count
    )
}

fun MovieDTO.toMovieEntity(
    category: String?
): MovieEntity {
    return MovieEntity(
        id ?: -1,
        category ?: "",
        adult ?: false,
        backdrop_path ?: "",
        genre_ids = try {
            genre_ids?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },
        original_language ?: "",
        original_title ?: "",
        overview ?: "",
        popularity ?: -1.0,
        poster_path ?: "",
        release_date ?: "",
        title ?: "",
        video ?: false,
        vote_average ?: -1.0,
        vote_count ?: -1
    )
}

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

fun MovieDetailDTO.toMovieDetailEntity(): MovieDetailEntity {
    return MovieDetailEntity(
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

fun MovieDetailEntity.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id,
        adult,
        backdrop_path,
        belongs_to_collection,
        budget,
        genres,
        homepage,
        imdb_id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        production_companies,
        production_countries,
        release_date,
        revenue,
        runtime,
        spoken_languages,
        status,
        tagline,
        title,
        video,
        vote_average,
        vote_count
    )
}