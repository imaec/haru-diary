package com.imaec.feature.notificationsetting

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.component.dialog.PermissionDialog
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.gray100
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable
import com.imaec.core.utils.utils.CheckNotificationPermission

@Composable
fun NotificationSettingScreen(viewModel: NotificationViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isNotificationOn) {
        CheckNotificationPermission(
            permissionDialog = { onDismiss ->
                PermissionDialog(
                    onDismiss = {
                        onDismiss()
                        viewModel.toggleNotification(isChecked = false)
                    }
                )
            }
        )
    }

    NotificationSettingScreen(
        isNotificationOn = uiState.isNotificationOn,
        isAm = uiState.isAm,
        hour = uiState.hour,
        minute = uiState.minute,
        onNotificationToggle = viewModel::toggleNotification,
        onTimeSelected = viewModel::updateNotificationTime
    )
}

@Composable
private fun NotificationSettingScreen(
    isNotificationOn: Boolean,
    isAm: Boolean,
    hour: Int,
    minute: Int,
    onNotificationToggle: (Boolean) -> Unit,
    onTimeSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HaruAppBar(title = stringResource(R.string.notification_setting))
        },
        content = { contentPadding ->
            NotificationSettingContent(
                contentPadding = contentPadding,
                isNotificationOn = isNotificationOn,
                isAm = isAm,
                hour = hour,
                minute = minute,
                onNotificationToggle = onNotificationToggle,
                onTimeSelected = onTimeSelected
            )
        }
    )
}

@Composable
private fun NotificationSettingContent(
    contentPadding: PaddingValues,
    isNotificationOn: Boolean,
    isAm: Boolean,
    hour: Int,
    minute: Int,
    onNotificationToggle: (Boolean) -> Unit,
    onTimeSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit
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
                isNotificationOn = isNotificationOn,
                onNotificationToggle = onNotificationToggle
            )
        }
        item {
            NotificationTimeItem(
                isNotificationOn = isNotificationOn,
                isAm = isAm,
                hour = hour,
                minute = minute,
                onTimeSelected = onTimeSelected
            )
        }
    }
}

@Composable
private fun NotificationSettingItem(
    isNotificationOn: Boolean,
    onNotificationToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.notification),
            style = body2(),
            color = AppTheme.colors.onBackground
        )
        Switch(
            checked = isNotificationOn,
            onCheckedChange = onNotificationToggle
        )
    }
}

@Composable
private fun NotificationTimeItem(
    isNotificationOn: Boolean,
    isAm: Boolean,
    hour: Int,
    minute: Int,
    onTimeSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit
) {
    var showTimeBottomSheet by remember { mutableStateOf(false) }
    val notificationTime = stringResource(
        R.string.notification_setting_time,
        if (isAm) {
            stringResource(R.string.am)
        } else {
            stringResource(R.string.pm)
        },
        if (hour < 10) "0$hour" else hour,
        if (minute < 10) "0$minute" else minute
    )

    if (showTimeBottomSheet) {
        TimeDialog(
            currentAmPm = isAm,
            currentHour = hour,
            currentMinute = minute,
            onTimeSelected = onTimeSelected,
            onDismiss = { showTimeBottomSheet = false }
        )
    }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .singleClickable(enabled = isNotificationOn) {
                    showTimeBottomSheet = true
                }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.notification_time),
                style = body2(),
                color = AppTheme.colors.onBackground
            )
            Text(
                text = notificationTime,
                style = body1().copy(fontWeight = FontWeight.SemiBold),
                color = AppTheme.colors.onBackground
            )
        }
        if (!isNotificationOn) {
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
private fun NotificationSettingScreenOnPreview() {
    AppTheme {
        NotificationSettingScreen(
            isNotificationOn = true,
            isAm = true,
            hour = 9,
            minute = 0,
            onNotificationToggle = {},
            onTimeSelected = { _, _, _ -> }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NotificationSettingScreenOffPreview() {
    AppTheme {
        NotificationSettingScreen(
            isNotificationOn = false,
            isAm = true,
            hour = 9,
            minute = 0,
            onNotificationToggle = {},
            onTimeSelected = { _, _, _ -> }
        )
    }
}
