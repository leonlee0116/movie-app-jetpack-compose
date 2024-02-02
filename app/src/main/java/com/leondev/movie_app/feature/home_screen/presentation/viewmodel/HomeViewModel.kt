package com.leondev.movie_app.feature.home_screen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeScreenUIState = MutableStateFlow(HomeScreenUIState())
    val homeScreenUIState = _homeScreenUIState.asStateFlow()
    fun onEvent(event: HomeScreenUIEvent) {
        when (event) {
            is HomeScreenUIEvent.Navigate -> {
                _homeScreenUIState.update {
                    it.copy(
                        currentPage = event.page
                    )
                }
            }
        }
    }
}