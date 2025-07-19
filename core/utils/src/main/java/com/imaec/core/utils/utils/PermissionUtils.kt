package com.imaec.core.utils.utils

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckNotificationPermission(
    onPermissionResult: (Boolean) -> Unit = {},
    permissionDialog: @Composable (onDismiss: () -> Unit) -> Unit = {}
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionState = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
        var showDialog by remember(permissionState.status) {
            mutableStateOf(permissionState.status.isPermanentlyDenied())
        }

        // Permission 상태에 따른 처리
        LaunchedEffect(permissionState.status) {
            if (permissionState.status.isGranted) {
                onPermissionResult(true)
            }
        }

        if (showDialog) {
            permissionDialog {
                showDialog = false
                onPermissionResult(permissionState.status.isGranted)
            }
        } else {
            NotificationPermissionRequest()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermissionRequest(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        var isGranted by rememberSaveable { mutableStateOf(false) }
        val permissionState = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            onPermissionResult = {
                isGranted = it
            }
        )

        LaunchedEffect(lifecycleOwner) {
            lifecycleOwner.repeatOnLifecycle(minActiveState) {
                if (!isGranted) {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun PermissionStatus.isPermanentlyDenied(): Boolean {
    return !shouldShowRationale && !this.isGranted
}
