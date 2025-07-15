package com.imaec.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.utils.extension.from
import com.imaec.core.utils.utils.formatDate
import com.imaec.core.utils.utils.getYearMonthDay
import com.imaec.domain.usecase.diary.GetDiariesByMonthUseCase
import com.imaec.domain.usecase.diary.ToggleDiaryLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDiariesByMonthUseCase: GetDiariesByMonthUseCase,
    private val toggleDiaryLikeUseCase: ToggleDiaryLikeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchDiariesByMonth(month = Date().formatDate(format = "yyyy-MM"))
    }

    private fun fetchDiariesByMonth(month: String) {
        viewModelScope.launch {
            getDiariesByMonthUseCase(month)
                .collect { diaries ->
                    _uiState.value = uiState.value.copy(diaries = diaries.map(DiaryVo::fromDto))
                }
        }
    }

    fun selectDate(year: Int, month: Int, day: Int) {
        if (month != uiState.value.selectedDate.second) {
            fetchDiariesByMonth(month = "%04d-%02d".from(year, month))
        }
        _uiState.value = uiState.value.copy(selectedDate = Triple(year, month, day))
    }

    fun toggleDiaryLike(diaryVo: DiaryVo) {
        viewModelScope.launch {
            toggleDiaryLikeUseCase(DiaryVo.toDto(diaryVo))
        }
    }
}

data class HomeUiState(
    val diaries: List<DiaryVo> = emptyList(),
    val year: Int = Calendar.getInstance().get(Calendar.YEAR),
    val month: Int = Calendar.getInstance().get(Calendar.MONTH),
    val selectedDate: Triple<Int, Int, Int> = run { Calendar.getInstance().getYearMonthDay() }
)
