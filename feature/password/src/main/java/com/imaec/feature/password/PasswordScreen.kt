package com.imaec.feature.password

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.dialog.BiometricAuthDialog
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.display2
import com.imaec.core.designsystem.theme.h2
import com.imaec.core.model.setting.PasswordType
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable

@Composable
fun PasswordScreen(viewModel: PasswordViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectEvent(viewModel = viewModel)

    BackHandler(enabled = uiState.passwordType == PasswordType.LAUNCH) {
        // Launch Type의 비밀번호 입력 화면의 경우 뒤로가기 동작 막음
    }

    PasswordScreen(
        passwordType = uiState.passwordType,
        passwordTitle = uiState.passwordTitle,
        password = uiState.inputPassword,
        onNumberClick = viewModel::updatePassword,
        onRemoveClick = viewModel::removePassword
    )
}

@Composable
private fun CollectEvent(viewModel: PasswordViewModel) {
    val appNavigator = LocalAppNavigator.current
    var showBiometricDialog by remember { mutableStateOf(false) }
    var passwordTitleType by remember { mutableStateOf(PasswordTitleType.ENTER_PASSWORD) }
    val passwordTitle = getPasswordTitle(passwordTitleType = passwordTitleType)

    LaunchedEffect(passwordTitleType) {
        viewModel.updatePasswordTitle(passwordTitle)
    }

    if (showBiometricDialog) {
        BiometricAuthDialog(
            onAuthenticated = {
                showBiometricDialog = false
                appNavigator.popUp()
            },
            onFailed = {
                showBiometricDialog = false
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is PasswordEvent.UpdateTitle -> {
                    passwordTitleType = it.passwordTitleType
                }
                PasswordEvent.PopUp -> appNavigator.popUp()
                PasswordEvent.ShowBiometricDialog -> {
                    showBiometricDialog = true
                }
            }
        }
    }
}

@Composable
private fun getPasswordTitle(passwordTitleType: PasswordTitleType): String {
    return when (passwordTitleType) {
        PasswordTitleType.ENTER_NEW_PASSWORD -> {
            stringResource(R.string.enter_new_password)
        }
        PasswordTitleType.ENTER_PASSWORD -> {
            stringResource(R.string.enter_password)
        }
        PasswordTitleType.ENTER_CURRENT_PASSWORD -> {
            stringResource(R.string.enter_current_password)
        }
        PasswordTitleType.REENTER_PASSWORD -> {
            stringResource(R.string.reenter_password)
        }
        PasswordTitleType.PASSWORD_INCORRECT_ENTER_PASSWORD -> {
            stringResource(R.string.password_incorrect) +
                "\n${stringResource(R.string.enter_password)}"
        }
        PasswordTitleType.PASSWORD_INCORRECT_ENTER_CURRENT_PASSWORD -> {
            stringResource(R.string.password_incorrect) +
                "\n${stringResource(R.string.enter_current_password)}"
        }
        PasswordTitleType.PASSWORD_INCORRECT_REENTER_PASSWORD -> {
            stringResource(R.string.password_incorrect) +
                "\n${stringResource(R.string.reenter_password)}"
        }
    }
}

@Composable
private fun PasswordScreen(
    passwordType: PasswordType,
    passwordTitle: String,
    password: String,
    onNumberClick: (String) -> Unit,
    onRemoveClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (passwordType != PasswordType.LAUNCH) {
                PasswordTopBar()
            }
        },
        content = { contentPadding ->
            PasswordContent(
                contentPadding = contentPadding,
                passwordTitle = passwordTitle,
                password = password,
                onNumberClick = onNumberClick,
                onRemoveClick = onRemoveClick
            )
        }
    )
}

@Composable
private fun PasswordTopBar() {
    val appNavigator = LocalAppNavigator.current

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .singleClickable { appNavigator.popUp() }
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "close",
            tint = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun PasswordContent(
    contentPadding: PaddingValues,
    passwordTitle: String,
    password: String,
    onNumberClick: (String) -> Unit,
    onRemoveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(bottom = 36.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 120.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = passwordTitle,
                textAlign = TextAlign.Center,
                style = h2().copy(fontWeight = FontWeight.Medium),
                color = AppTheme.colors.onBackground
            )
            Password(password = password)
        }
        KeyPad(
            onNumberClick = onNumberClick,
            onRemoveClick = onRemoveClick
        )
    }
}

@Composable
private fun Password(password: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (it < password.length) {
                            AppTheme.colors.tertiary
                        } else {
                            AppTheme.colors.primaryContainer
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun KeyPad(
    onNumberClick: (String) -> Unit,
    onRemoveClick: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 36.dp),
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3)
    ) {
        items(12) {
            val text = when (it) {
                9 -> ""
                10 -> "0"
                11 -> "back"
                else -> "${it + 1}"
            }
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(AppTheme.colors.background)
                        .clip(CircleShape)
                        .clickable(enabled = text != "") {
                            if (text == "back") {
                                onRemoveClick()
                            } else {
                                onNumberClick(text)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (text == "back") {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.ic_eraser),
                            tint = AppTheme.colors.onBackground,
                            contentDescription = "eraser"
                        )
                    } else {
                        Text(
                            text = text,
                            style = display2().copy(fontWeight = FontWeight.Normal),
                            color = AppTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordScreenLaunchPreview() {
    AppTheme {
        PasswordScreen(
            passwordType = PasswordType.LAUNCH,
            passwordTitle = "비밀번호를 입력해 주세요",
            password = "12",
            onNumberClick = {},
            onRemoveClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordScreenNewPreview() {
    AppTheme {
        PasswordScreen(
            passwordType = PasswordType.NEW,
            passwordTitle = "새 비밀번호를 입력해 주세요",
            password = "12",
            onNumberClick = {},
            onRemoveClick = {}
        )
    }
}
