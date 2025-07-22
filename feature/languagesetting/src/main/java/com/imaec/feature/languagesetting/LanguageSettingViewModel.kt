package com.imaec.feature.languagesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.setting.LanguageType
import com.imaec.domain.usecase.setting.GetLanguageTypeUseCase
import com.imaec.domain.usecase.setting.SaveLanguageTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSettingViewModel @Inject constructor(
    private val getLanguageTypeUseCase: GetLanguageTypeUseCase,
    private val saveLanguageTypeUseCase: SaveLanguageTypeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LanguageSettingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchLanguageType()
    }

    private fun fetchLanguageType() {
        viewModelScope.launch {
            getLanguageTypeUseCase().collect { languageType ->
                _uiState.value = uiState.value.copy(selectedLanguageType = languageType)
            }
        }
    }

    fun selectLanguageType(languageType: LanguageType) {
        viewModelScope.launch {
            saveLanguageTypeUseCase(languageType)
        }
    }
}

data class LanguageSettingUiState(
    val selectedLanguageType: LanguageType = LanguageType.SYSTEM
)
