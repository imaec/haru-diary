package com.imaec.feature.likeddiarylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.model.diary.DiaryVo
import com.imaec.domain.usecase.diary.GetLikedDiariesUseCase
import com.imaec.domain.usecase.diary.ToggleDiaryLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedDiaryListViewModel @Inject constructor(
    private val getLikedDiariesUseCase: GetLikedDiariesUseCase,
    private val toggleDiaryLikeUseCase: ToggleDiaryLikeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiaryListUiState())
    val uiState: StateFlow<DiaryListUiState> = _uiState.asStateFlow()

    init {
        fetchDiaries()
    }

    private fun fetchDiaries() {
        viewModelScope.launch {
            getLikedDiariesUseCase().collect { diaries ->
                _uiState.value = uiState.value.copy(diaries = diaries.map(DiaryVo::fromDto))
            }
        }
    }

    fun toggleDiaryLike(diaryVo: DiaryVo) {
        viewModelScope.launch {
            toggleDiaryLikeUseCase(DiaryVo.toDto(diaryVo))
        }
    }
}

data class DiaryListUiState(
    val diaries: List<DiaryVo> = emptyList()
)
