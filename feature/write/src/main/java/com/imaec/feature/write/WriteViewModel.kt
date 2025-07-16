package com.imaec.feature.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.navigation.utils.DIARY_ID
import com.imaec.core.utils.utils.formatDate
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType
import com.imaec.domain.usecase.diary.DeleteDiaryUseCase
import com.imaec.domain.usecase.diary.GetDiaryByIdUseCase
import com.imaec.domain.usecase.diary.UpdateDiaryUseCase
import com.imaec.domain.usecase.diary.WriteDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDiaryByIdUseCase: GetDiaryByIdUseCase,
    private val writeDiaryUseCase: WriteDiaryUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<WriteEvent>()
    val event = _event.asSharedFlow()

    init {
        savedStateHandle.get<Long>(DIARY_ID)?.let(::fetchDiaryById)
    }

    private fun fetchDiaryById(diaryId: Long) {
        viewModelScope.launch {
            getDiaryByIdUseCase(diaryId)?.let(DiaryVo::fromDto)?.let(::setDiary)
        }
    }

    private fun setDiary(diary: DiaryVo) {
        _uiState.value = uiState.value.copy(
            id = diary.id,
            writeType = WriteType.UPDATE,
            date = diary.date,
            weatherTypes = WeatherType.entries.map { weather ->
                weather to (weather == diary.weather)
            },
            moodTypes = MoodType.entries.map { mood ->
                mood to (mood == diary.mood)
            },
            content = diary.content
        )
    }

    fun updateDate(date: String) {
        _uiState.value = uiState.value.copy(date = date)
    }

    fun updateWeather(weatherType: WeatherType) {
        _uiState.value = uiState.value.copy(
            weatherTypes = uiState.value.weatherTypes.map {
                it.first to (it.first == weatherType)
            }
        )
    }

    fun updateMood(moodType: MoodType) {
        _uiState.value = uiState.value.copy(
            moodTypes = uiState.value.moodTypes.map {
                it.first to (it.first == moodType)
            }
        )
    }

    fun updateContent(content: String) {
        _uiState.value = uiState.value.copy(content = content)
    }

    fun writeDiary() {
        viewModelScope.launch {
            if (uiState.value.content.isEmpty()) {
                _event.emit(WriteEvent.ShowContentEmptyMessage)
                return@launch
            }

            val result = writeDiary(uiState = uiState.value)
            if (result) {
                _event.emit(
                    WriteEvent.PopUp(popUpType = getPopupType(writeType = uiState.value.writeType))
                )
            }
        }
    }

    private suspend fun writeDiary(uiState: WriteUiState): Boolean = when (uiState.writeType) {
        WriteType.WRITE -> writeDiaryUseCase(uiState.toDiaryDto()) > 0
        WriteType.UPDATE -> updateDiaryUseCase(uiState.toDiaryDto()) > 0
    }

    private fun getPopupType(writeType: WriteType) = if (writeType == WriteType.WRITE) {
        WritePopUpType.WRITE
    } else {
        WritePopUpType.UPDATE
    }

    fun deleteDiary() {
        viewModelScope.launch {
            if (deleteDiaryUseCase(uiState.value.id) > 0) {
                _event.emit(WriteEvent.PopUp(popUpType = WritePopUpType.DELETE))
            }
        }
    }
}

data class WriteUiState(
    val id: Long = 0,
    val writeType: WriteType = WriteType.WRITE,
    val date: String = Date().formatDate(format = "yyyy-MM-dd"),
    val weatherTypes: List<Pair<WeatherType, Boolean>> = WeatherType.toSelectableList(),
    val moodTypes: List<Pair<MoodType, Boolean>> = MoodType.toSelectableList(),
    val content: String = ""
) {
    fun toDiaryDto(): DiaryDto = DiaryDto(
        id = id,
        date = date,
        weather = weatherTypes.first { it.second }.first,
        mood = moodTypes.first { it.second }.first,
        content = content,
        isLiked = false
    )
}

sealed interface WriteEvent {

    data object ShowContentEmptyMessage : WriteEvent

    data class PopUp(val popUpType: WritePopUpType) : WriteEvent
}

enum class WritePopUpType {
    WRITE,
    UPDATE,
    DELETE
}
