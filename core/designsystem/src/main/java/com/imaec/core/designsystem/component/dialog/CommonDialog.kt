package com.imaec.core.designsystem.component.dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.utils.extension.singleClickable

@Composable
fun CommonDialog(
    message: String,
    title: String? = null,
    positiveButtonText: String = "확인",
    negativeButtonText: String? = null,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        CommonDialog(
            title = title,
            message = message,
            positiveButtonText = positiveButtonText,
            negativeButtonText = negativeButtonText,
            onClickPositiveButton = onPositiveButtonClick,
            onClickNegativeButton = onNegativeButtonClick
        )
    }
}

@Composable
private fun CommonDialog(
    title: String?,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String? = null,
    onClickPositiveButton: () -> Unit = {},
    onClickNegativeButton: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.background, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = h3(),
                    color = AppTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            Text(
                text = message,
                style = body1().copy(fontWeight = FontWeight.Normal),
                color = AppTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (negativeButtonText != null) {
                    DialogButton(
                        modifier = Modifier.weight(1f),
                        buttonText = negativeButtonText,
                        onClick = onClickNegativeButton
                    )
                }
                DialogButton(
                    modifier = Modifier.weight(1f),
                    buttonText = positiveButtonText,
                    onClick = onClickPositiveButton
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier,
    buttonText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .background(
                    color = AppTheme.colors.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .weight(1f)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .singleClickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                style = body1().copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CommonDialogTitleMessagePreview() {
    AppTheme {
        CommonDialog(
            title = "다이얼로그 타이틀",
            message = "다이얼로그 내용",
            onDismiss = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CommonDialogMessagePreview() {
    AppTheme {
        CommonDialog(
            message = "다이얼로그 내용",
            onDismiss = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CommonDialogTitleMessageNegativePreview() {
    AppTheme {
        CommonDialog(
            title = "다이얼로그 타이틀",
            message = "다이얼로그 내용",
            negativeButtonText = "취소",
            onDismiss = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CommonDialogMessageNegativePreview() {
    AppTheme {
        CommonDialog(
            message = "다이얼로그 내용",
            negativeButtonText = "취소",
            onDismiss = {}
        )
    }
}
