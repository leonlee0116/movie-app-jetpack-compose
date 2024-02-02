package com.leondev.movie_app.feature.movie_detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leondev.movie_app.feature.movie_detail.domain.repository.MovieDetailRepository
import com.leondev.movie_app.feature.movie_detail.presentation.MovieDetailUIState
import com.leondev.movie_app.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movieDetailUIState = MutableStateFlow(MovieDetailUIState())
    val movieDetailUIState = _movieDetailUIState.asStateFlow()

    private val movieId = savedStateHandle.get<Int>("movieId")

    init {
        loadMovieDetail(movieId = movieId ?: 0)
    }

    private fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieDetailRepository.getMovieDetail(movieId.toString())
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _movieDetailUIState.update {
                                it.copy(
                                    isLoading = false
                                )
                            }

                        }

                        is Resource.Loading -> {
                            _movieDetailUIState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }

                        }

                        is Resource.Success -> {
                            _movieDetailUIState.update {
                                it.copy(
                                    movieDetail = resource.data,
                                    isLoading = false
                                )
                            }

                        }
                    }
                }

        }
    }

}