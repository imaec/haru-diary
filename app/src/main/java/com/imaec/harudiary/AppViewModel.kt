package com.imaec.harudiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.usecase.setting.GetFontTypeUseCase
import com.imaec.domain.usecase.setting.IsLockOnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getFontTypeUseCase: GetFontTypeUseCase,
    private val isLockOnUseCase: IsLockOnUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFontType()
        fetchLockOn()
    }

    private fun fetchFontType() {
        viewModelScope.launch {
            getFontTypeUseCase().collect { fontType ->
                _uiState.value = uiState.value.copy(fontType = fontType)
            }
        }
    }

    private fun fetchLockOn() {
        viewModelScope.launch {
            isLockOnUseCase().collect { isLockOn ->
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    isLockOn = isLockOn
                )
            }
        }
    }
}

data class AppUiState(
    val isLoading: Boolean = true,
    val fontType: FontType = FontType.DEFAULT,
    val isLockOn: Boolean = false
)
