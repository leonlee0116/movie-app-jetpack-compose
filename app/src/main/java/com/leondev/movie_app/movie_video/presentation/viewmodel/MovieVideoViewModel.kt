package com.leondev.movie_app.movie_video.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leondev.movie_app.movie_video.data.repository.MovieVideoRepositoryImpl
import com.leondev.movie_app.movie_video.domain.model.MovieVideoResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVideoViewModel @Inject constructor(
    private val movieVideoRepository: MovieVideoRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movieVideoResult = MutableStateFlow(listOf<MovieVideoResults>())
    val movieVideoResult = _movieVideoResult.asStateFlow()

    private val movieId = savedStateHandle.get<Int>("movieId")

    init {
        getMovieVideo(movieId ?: 0)
    }

    fun getMovieVideo(movieId: Int) {
        viewModelScope.launch {
            movieVideoRepository.getMovieVideo(movieId)
                .catch {
                    println(it.message)
                }
                .collect {
                    it.data?.let { results ->
                        _movieVideoResult.value = results
                    }
                }
        }
    }
}