package com.imaec.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.utils.extension.comma
import com.imaec.core.utils.utils.diffDays
import com.imaec.domain.usecase.diary.GetDiariesCountUseCase
import com.imaec.domain.usecase.diary.GetLikedDiariesCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getDiariesCountUseCase: GetDiariesCountUseCase,
    private val getLikedDiariesCountUseCase: GetLikedDiariesCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchDiariesCount()
        fetchLikedDiariesCount()
    }

    private fun fetchDiariesCount() {
        viewModelScope.launch {
            getDiariesCountUseCase().collect { count ->
                _uiState.value = uiState.value.copy(diariesCount = count.comma())
            }
        }
    }

    private fun fetchLikedDiariesCount() {
        viewModelScope.launch {
            getLikedDiariesCountUseCase().collect { count ->
                _uiState.value = uiState.value.copy(likedDiariesCount = count.comma())
            }
        }
    }

    fun setInstallTime(installedTime: Long) {
        _uiState.value = uiState.value.copy(togetherDays = installedTime.diffDays().comma())
    }
}

data class MyUiState(
    val togetherDays: String = "1",
    val diariesCount: String = "0",
    val likedDiariesCount: String = "0"
)
