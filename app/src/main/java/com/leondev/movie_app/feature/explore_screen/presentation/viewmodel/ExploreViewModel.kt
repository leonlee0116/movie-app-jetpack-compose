package com.leondev.movie_app.feature.explore_screen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leondev.movie_app.feature.explore_screen.domain.repository.ExploreRepository
import com.leondev.movie_app.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository
) : ViewModel() {
    private val _exploreUIState = MutableStateFlow(ExploreUIState())
    val exploreUIState = _exploreUIState.asStateFlow()

    init {
        getExploreMovieList()
    }

    private fun getExploreMovieList() {
        viewModelScope.launch {
            exploreRepository.getExploreMovieList(_exploreUIState.value.currentPage)
                .catch {

                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _exploreUIState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _exploreUIState.update {
                                it.copy(isLoading = resource.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            resource.data?.let { result ->
                                _exploreUIState.update {
                                    it.copy(
                                        exploreMovieList = it.exploreMovieList + result.shuffled(),
                                        currentPage = it.currentPage + 1,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }

                }
        }
    }
}