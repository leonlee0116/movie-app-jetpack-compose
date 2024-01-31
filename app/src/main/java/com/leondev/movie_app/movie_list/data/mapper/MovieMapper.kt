package com.leondev.movie_app.movie_list.data.mapper

import com.leondev.movie_app.movie_list.data.local.entity.MovieEntity
import com.leondev.movie_app.movie_list.data.remote.dto.MovieDTO
import com.leondev.movie_app.movie_list.domain.model.Movie

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