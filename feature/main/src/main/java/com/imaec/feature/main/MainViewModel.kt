package com.imaec.feature.main

import androidx.lifecycle.ViewModel
import com.imaec.core.navigation.navigator.main.MainRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun selectBottomBarItem(route: MainRoute) {
        _uiState.value = _uiState.value.copy(selectedRoute = route)
    }
}

data class MainUiState(
    val selectedRoute: MainRoute = MainRoute.Home
)
