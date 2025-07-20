package com.imaec.feature.darkmodesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.usecase.setting.GetDarkModeTypeUseCase
import com.imaec.domain.usecase.setting.SaveDarkModeTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DarkModeSettingViewModel @Inject constructor(
    private val getDarkModeTypeUseCase: GetDarkModeTypeUseCase,
    private val saveDarkModeTypeUseCase: SaveDarkModeTypeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DarkModeSettingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchDarkModeType()
    }

    private fun fetchDarkModeType() {
        viewModelScope.launch {
            getDarkModeTypeUseCase().collect { darkModeType ->
                _uiState.value = _uiState.value.copy(selectedDarkModeType = darkModeType)
            }
        }
    }

    fun selectDarkModeType(darkModeType: DarkModeType) {
        viewModelScope.launch {
            saveDarkModeTypeUseCase(darkModeType)
        }
    }
}

data class DarkModeSettingUiState(
    val selectedDarkModeType: DarkModeType = DarkModeType.SYSTEM
)
