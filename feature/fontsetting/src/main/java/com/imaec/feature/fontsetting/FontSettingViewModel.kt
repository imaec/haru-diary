package com.imaec.feature.fontsetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.usecase.setting.GetFontTypeUseCase
import com.imaec.domain.usecase.setting.SaveFontTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FontSettingViewModel @Inject constructor(
    private val getFontTypeUseCase: GetFontTypeUseCase,
    private val saveFontTypeUseCase: SaveFontTypeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FontSettingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchFontType()
    }

    private fun fetchFontType() {
        viewModelScope.launch {
            getFontTypeUseCase().collect { fontType ->
                _uiState.value = uiState.value.copy(selectedFontType = fontType)
            }
        }
    }

    fun saveFontType(fontType: FontType) {
        viewModelScope.launch {
            saveFontTypeUseCase(fontType)
        }
    }
}

data class FontSettingUiState(
    val selectedFontType: FontType = FontType.DEFAULT
)
