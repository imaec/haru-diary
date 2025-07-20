package com.imaec.feature.locksetting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.component.dialog.BiometricAuthDialog
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.gray100
import com.imaec.core.model.setting.PasswordType
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable

@Composable
fun LockSettingScreen(viewModel: LockSettingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CollectEvent(viewModel = viewModel)

    LockSettingScreen(
        isLockOn = uiState.isLockOn,
        isFingerPrintOn = uiState.isFingerPrintOn,
        onLockToggle = viewModel::toggleLock,
        onFingerPrintToggle = { isChecked ->
            if (isChecked) {
                viewModel.showBiometricDialog()
            } else {
                viewModel.toggleFingerPrint(isChecked = false)
            }
        }
    )
}

@Composable
private fun CollectEvent(viewModel: LockSettingViewModel) {
    val appNavigator = LocalAppNavigator.current
    var showBiometricDialog by remember { mutableStateOf(false) }

    if (showBiometricDialog) {
        BiometricAuthDialog(
            onAuthenticated = {
                showBiometricDialog = false
                viewModel.toggleFingerPrint(isChecked = true)
            },
            onFailed = {
                showBiometricDialog = false
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is LockSettingEvent.NavigatePasswordScreen -> {
                    appNavigator.navigate(
                        route = AppRoute.Password(passwordType = it.passwordType.name)
                    )
                }
                LockSettingEvent.ShowBiometricDialog -> {
                    showBiometricDialog = true
                }
            }
        }
    }
}

@Composable
private fun LockSettingScreen(
    isLockOn: Boolean,
    isFingerPrintOn: Boolean,
    onLockToggle: (Boolean) -> Unit,
    onFingerPrintToggle: (Boolean) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HaruAppBar(title = stringResource(R.string.lock_setting))
        },
        content = { contentPadding ->
            LockSettingContent(
                contentPadding = contentPadding,
                isLockOn = isLockOn,
                isFingerPrintOn = isFingerPrintOn,
                onLockToggle = onLockToggle,
                onFingerPrintToggle = onFingerPrintToggle
            )
        }
    )
}

@Composable
private fun LockSettingContent(
    contentPadding: PaddingValues,
    isLockOn: Boolean,
    isFingerPrintOn: Boolean,
    onLockToggle: (Boolean) -> Unit,
    onFingerPrintToggle: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        item {
            NotificationSettingItem(
                title = stringResource(R.string.screen_lock),
                isChecked = isLockOn,
                onSwitchToggle = onLockToggle
            )
        }
        item {
            NotificationSettingItem(
                title = stringResource(R.string.biometric_authentication),
                isChecked = isFingerPrintOn,
                isEnable = isLockOn,
                onSwitchToggle = onFingerPrintToggle
            )
        }
        item {
            NotificationSettingItem(
                title = stringResource(R.string.change_password),
                isEnable = isLockOn,
                hasSwitch = false
            )
        }
    }
}

@Composable
private fun NotificationSettingItem(
    title: String,
    isChecked: Boolean = true,
    isEnable: Boolean = true,
    hasSwitch: Boolean = true,
    onSwitchToggle: (Boolean) -> Unit = {}
) {
    val appNavigator = LocalAppNavigator.current

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .singleClickable(enabled = !hasSwitch) {
                    appNavigator.navigate(
                        AppRoute.Password(passwordType = PasswordType.UPDATE.name)
                    )
                }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = body2(),
                color = AppTheme.colors.onBackground
            )
            if (hasSwitch) {
                Switch(
                    checked = isChecked,
                    enabled = isEnable,
                    onCheckedChange = { isChecked ->
                        onSwitchToggle(isChecked)
                    }
                )
            }
        }
        if (!isEnable) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(gray100.copy(alpha = 0.5f))
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LockSettingScreenOnPreview() {
    AppTheme {
        LockSettingScreen(
            isLockOn = true,
            isFingerPrintOn = true,
            onLockToggle = {},
            onFingerPrintToggle = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LockSettingScreenOffPreview() {
    AppTheme {
        LockSettingScreen(
            isLockOn = false,
            isFingerPrintOn = true,
            onLockToggle = {},
            onFingerPrintToggle = {}
        )
    }
}
