package com.imaec.feature.notificationsetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.worker.DailyNotificationScheduler
import com.imaec.domain.usecase.setting.GetNotificationTimeUseCase
import com.imaec.domain.usecase.setting.IsNotificationOnUseCase
import com.imaec.domain.usecase.setting.SaveNotificationOnUseCase
import com.imaec.domain.usecase.setting.SaveNotificationTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val dailyNotificationScheduler: DailyNotificationScheduler,
    private val isNotificationOnUseCase: IsNotificationOnUseCase,
    private val saveNotificationOnUseCase: SaveNotificationOnUseCase,
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
    private val saveNotificationTimeUseCase: SaveNotificationTimeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchNotificationOn()
        fetchNotificationTime()
    }

    private fun fetchNotificationOn() {
        viewModelScope.launch {
            isNotificationOnUseCase().collect { isNotificationOn ->
                _uiState.value = _uiState.value.copy(isNotificationOn = isNotificationOn)
            }
        }
    }

    private fun fetchNotificationTime() {
        viewModelScope.launch {
            getNotificationTimeUseCase().collect { (isAm, hour, minute) ->
                _uiState.value = _uiState.value.copy(
                    isAm = isAm,
                    hour = hour,
                    minute = minute
                )
            }
        }
    }

    fun toggleNotification(isChecked: Boolean) {
        viewModelScope.launch {
            saveNotificationOnUseCase(isChecked)
            if (isChecked) {
                registerScheduler(
                    isAm = uiState.value.isAm,
                    hour = uiState.value.hour,
                    minute = uiState.value.minute
                )
            } else {
                cancelScheduler()
            }
        }
    }

    fun updateNotificationTime(isAm: Boolean, hour: Int, minute: Int) {
        viewModelScope.launch {
            saveNotificationTimeUseCase(
                if (isAm) {
                    "오전 $hour:$minute"
                } else {
                    "오후 $hour:$minute"
                }
            )
            registerScheduler(
                isAm = isAm,
                hour = hour,
                minute = minute
            )
        }
    }

    private fun registerScheduler(isAm: Boolean, hour: Int, minute: Int) {
        dailyNotificationScheduler.schedule(
            hour = if (isAm) hour else hour + 12,
            minute = minute
        )
    }

    private fun cancelScheduler() {
        dailyNotificationScheduler.cancelSchedule()
    }
}

data class NotificationUiState(
    val isNotificationOn: Boolean = false,
    val isAm: Boolean = false,
    val hour: Int = 9,
    val minute: Int = 0
)
