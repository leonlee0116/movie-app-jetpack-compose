package com.leondev.movie_app.feature.movie_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leondev.movie_app.feature.movie_list.domain.repository.MovieListRepository
import com.leondev.movie_app.util.enum.MovieCategory
import com.leondev.movie_app.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {
    private var _movieListState = MutableStateFlow(MovieListUIState())
    val movieListState = _movieListState.asStateFlow()

    init {
        loadPopularMovie(true)
        loadTopRatedMovie(true)
    }

    fun onEvent(event: MovieListUIEvent) {
        when (event) {
            is MovieListUIEvent.Paginate -> {
                when (event.category) {
                    MovieCategory.POPULAR.name -> {
                        loadPopularMovie(true)
                    }

                    MovieCategory.TOP_RATED.name -> {
                        loadTopRatedMovie(true)
                    }

                    MovieCategory.NOW_PLAYING.name -> {

                    }

                    MovieCategory.UPCOMING.name -> {

                    }
                }
            }
        }
    }

    private fun loadTopRatedMovie(fetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(true, MovieCategory.TOP_RATED.category, 1)
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = resource.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            resource.data?.let { result ->
                                _movieListState.update {
                                    it.copy(
                                        topRatedMovieList = it.topRatedMovieList + result.shuffled(),
                                        topRatedMovieListPage = it.topRatedMovieListPage + 1,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun loadPopularMovie(fetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(true, MovieCategory.POPULAR.category, 1)
                .collectLatest {
                    when (it) {
                        is Resource.Error -> {
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Success -> {
                            it.data?.let { result ->
                                _movieListState.update {
                                    it.copy(
                                        popularMovieList = it.popularMovieList + result.shuffled(),
                                        popularMovieListPage = it.popularMovieListPage + 1
                                    )
                                }
                            }
                            _movieListState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                    _movieListState.update {
                        it.copy(isLoading = false)
                    }
                }
        }
    }
}