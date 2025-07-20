package com.imaec.feature.password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.core.model.setting.PasswordType
import com.imaec.core.navigation.utils.PASSWORD_TYPE
import com.imaec.domain.usecase.setting.CheckPasswordUseCase
import com.imaec.domain.usecase.setting.IsFingerPrintOnUseCase
import com.imaec.domain.usecase.setting.SaveLockOnUseCase
import com.imaec.domain.usecase.setting.SavePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val savePasswordUseCase: SavePasswordUseCase,
    private val checkPasswordUseCase: CheckPasswordUseCase,
    private val saveLockOnUseCase: SaveLockOnUseCase,
    private val isFingerPrintOnUseCase: IsFingerPrintOnUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<PasswordEvent>()
    val event = _event.asSharedFlow()

    init {
        val passwordTypeName = savedStateHandle.get<String>(PASSWORD_TYPE)
        setPasswordType(
            passwordType = PasswordType.fromString(passwordTypeName) ?: PasswordType.NEW
        )
        fetchIsFingerPrintOn()
    }

    private fun setPasswordType(passwordType: PasswordType) {
        _uiState.value = uiState.value.copy(passwordType = passwordType)
        viewModelScope.launch {
            delay(100)
            _event.emit(
                PasswordEvent.UpdateTitle(
                    passwordTitleType = when (passwordType) {
                        PasswordType.NEW -> PasswordTitleType.ENTER_NEW_PASSWORD
                        PasswordType.UPDATE -> PasswordTitleType.ENTER_CURRENT_PASSWORD
                        PasswordType.OFF -> PasswordTitleType.ENTER_CURRENT_PASSWORD
                        PasswordType.LAUNCH -> PasswordTitleType.ENTER_PASSWORD
                    }
                )
            )
        }
    }

    private fun fetchIsFingerPrintOn() {
        if (uiState.value.passwordType == PasswordType.LAUNCH) {
            viewModelScope.launch {
                isFingerPrintOnUseCase().collect { isFingerPrintOn ->
                    if (isFingerPrintOn) {
                        _event.emit(PasswordEvent.ShowBiometricDialog)
                    }
                }
            }
        }
    }

    fun updatePasswordTitle(passwordTitle: String) {
        _uiState.value = uiState.value.copy(passwordTitle = passwordTitle)
    }

    fun updatePassword(text: String) {
        if (uiState.value.inputPassword.length < 4) {
            _uiState.value = uiState.value.copy(
                inputPassword = uiState.value.inputPassword + text
            )
        }

        if (uiState.value.inputPassword.length == 4) {
            checkPassword()
        }
    }

    private fun checkPassword() {
        viewModelScope.launch {
            when (uiState.value.passwordType) {
                PasswordType.NEW -> checkNewPassword()
                PasswordType.UPDATE -> checkUpdatePassword()
                PasswordType.OFF -> checkOffPassword()
                PasswordType.LAUNCH -> checkLaunchPassword()
            }
        }
    }

    private suspend fun checkNewPassword() {
        // 새 비밀번호 입력 확인
        if (uiState.value.passwordStep == 1) {
            // 새 비밀번호 첫 번째 입력 확인
            _event.emit(
                PasswordEvent.UpdateTitle(passwordTitleType = PasswordTitleType.REENTER_PASSWORD)
            )
            _uiState.value = uiState.value.copy(
                passwordStep = 2,
                currentPassword = uiState.value.inputPassword,
                inputPassword = ""
            )
        } else {
            // 새 비밀번호 두 번째 입력 확인
            if (uiState.value.currentPassword == uiState.value.inputPassword) {
                // 비밀번호 저장
                savePasswordUseCase(uiState.value.inputPassword)
                saveLockOnUseCase(true)
                _event.emit(PasswordEvent.PopUp)
            } else {
                _event.emit(
                    PasswordEvent.UpdateTitle(
                        passwordTitleType = PasswordTitleType.PASSWORD_INCORRECT_REENTER_PASSWORD
                    )
                )
                _uiState.value = uiState.value.copy(inputPassword = "")
            }
        }
    }

    private suspend fun checkUpdatePassword() {
        val checkResult = checkPasswordUseCase(uiState.value.inputPassword)
        if (checkResult) {
            _event.emit(
                PasswordEvent.UpdateTitle(passwordTitleType = PasswordTitleType.ENTER_NEW_PASSWORD)
            )
            _uiState.value = uiState.value.copy(
                passwordType = PasswordType.NEW,
                inputPassword = ""
            )
        } else {
            _event.emit(
                PasswordEvent.UpdateTitle(
                    passwordTitleType = PasswordTitleType.PASSWORD_INCORRECT_ENTER_CURRENT_PASSWORD
                )
            )
            _uiState.value = uiState.value.copy(inputPassword = "")
        }
    }

    private suspend fun checkOffPassword() {
        val checkResult = checkPasswordUseCase(uiState.value.inputPassword)
        if (checkResult) {
            saveLockOnUseCase(false)
            _event.emit(PasswordEvent.PopUp)
        } else {
            _event.emit(
                PasswordEvent.UpdateTitle(
                    passwordTitleType = PasswordTitleType.PASSWORD_INCORRECT_ENTER_CURRENT_PASSWORD
                )
            )
            _uiState.value = uiState.value.copy(inputPassword = "")
        }
    }

    private suspend fun checkLaunchPassword() {
        val checkResult = checkPasswordUseCase(uiState.value.inputPassword)
        if (checkResult) {
            _event.emit(PasswordEvent.PopUp)
        } else {
            _event.emit(
                PasswordEvent.UpdateTitle(
                    passwordTitleType = PasswordTitleType.PASSWORD_INCORRECT_ENTER_PASSWORD
                )
            )
            _uiState.value = uiState.value.copy(inputPassword = "")
        }
    }

    fun removePassword() {
        _uiState.value = uiState.value.copy(
            inputPassword = uiState.value.inputPassword.dropLast(1)
        )
    }
}

data class PasswordUiState(
    val passwordType: PasswordType = PasswordType.NEW,
    val passwordStep: Int = 1,
    val passwordTitle: String = "",
    val currentPassword: String = "",
    val inputPassword: String = ""
)

sealed class PasswordEvent {

    data class UpdateTitle(val passwordTitleType: PasswordTitleType) : PasswordEvent()

    data object PopUp : PasswordEvent()

    data object ShowBiometricDialog : PasswordEvent()
}

enum class PasswordTitleType {
    ENTER_NEW_PASSWORD,
    ENTER_PASSWORD,
    ENTER_CURRENT_PASSWORD,
    REENTER_PASSWORD,
    PASSWORD_INCORRECT_ENTER_PASSWORD,
    PASSWORD_INCORRECT_ENTER_CURRENT_PASSWORD,
    PASSWORD_INCORRECT_REENTER_PASSWORD
}
