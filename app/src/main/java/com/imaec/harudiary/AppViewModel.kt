package com.imaec.harudiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.usecase.setting.GetFontTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getFontTypeUseCase: GetFontTypeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFontType()
    }

    private fun fetchFontType() {
        viewModelScope.launch {
            getFontTypeUseCase().collect { fontType ->
                _uiState.value = uiState.value.copy(fontType = fontType)
            }
        }
    }
}

data class AppUiState(
    val fontType: FontType = FontType.DEFAULT
)
