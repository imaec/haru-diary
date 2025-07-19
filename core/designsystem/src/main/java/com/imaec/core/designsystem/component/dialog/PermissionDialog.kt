package com.imaec.core.designsystem.component.dialog

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.imaec.core.designsystem.theme.AppTheme

@Composable
fun PermissionDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    CommonDialog(
        title = "알림 권한 설정",
        message = "알림 권한이 필요합니다.\n알림 권한 설정으로 이동하시겠습니까?",
        positiveButtonText = "설정",
        negativeButtonText = "취소",
        onPositiveButtonClick = {
            context.startActivity(intent)
        },
        onNegativeButtonClick = onDismiss
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PermissionDialogPreview() {
    AppTheme {
        PermissionDialog(
            onDismiss = {}
        )
    }
}
