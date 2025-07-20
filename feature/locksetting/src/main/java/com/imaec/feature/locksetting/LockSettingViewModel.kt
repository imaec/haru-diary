package com.imaec.feature.locksetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.model.setting.PasswordType
import com.imaec.domain.usecase.setting.IsFingerPrintOnUseCase
import com.imaec.domain.usecase.setting.IsLockOnUseCase
import com.imaec.domain.usecase.setting.SaveFingerPrintOnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSettingViewModel @Inject constructor(
    private val isLockOnUseCase: IsLockOnUseCase,
    private val isFingerPrintOnUseCase: IsFingerPrintOnUseCase,
    private val saveFingerPrintOnUseCase: SaveFingerPrintOnUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LockSettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LockSettingEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchLockOn()
        fetchFingerPrintOn()
    }

    private fun fetchLockOn() {
        viewModelScope.launch {
            isLockOnUseCase().collect {
                _uiState.value = uiState.value.copy(isLockOn = it)
            }
        }
    }

    private fun fetchFingerPrintOn() {
        viewModelScope.launch {
            isFingerPrintOnUseCase().collect {
                _uiState.value = uiState.value.copy(isFingerPrintOn = it)
            }
        }
    }

    fun toggleLock(isLockOn: Boolean) {
        viewModelScope.launch {
            _event.emit(
                LockSettingEvent.NavigatePasswordScreen(
                    passwordType = if (isLockOn) {
                        PasswordType.NEW
                    } else {
                        PasswordType.OFF
                    }
                )
            )
        }
    }

    fun showBiometricDialog() {
        viewModelScope.launch {
            _event.emit(LockSettingEvent.ShowBiometricDialog)
        }
    }

    fun toggleFingerPrint(isChecked: Boolean) {
        viewModelScope.launch {
            saveFingerPrintOnUseCase(isChecked)
        }
    }
}

data class LockSettingUiState(
    val isLockOn: Boolean = false,
    val isFingerPrintOn: Boolean = false
)

sealed class LockSettingEvent {

    data class NavigatePasswordScreen(val passwordType: PasswordType) : LockSettingEvent()

    data object ShowBiometricDialog : LockSettingEvent()
}
