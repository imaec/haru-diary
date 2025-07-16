package com.imaec.core.designsystem.component.snackbar

import android.content.res.Configuration
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.gray400
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

lateinit var snackbarHostState: SnackbarHostState

suspend fun showSnackbar(message: String) {
    coroutineScope {
        val snackbarJob = launch {
            snackbarHostState.showSnackbar(message = message)
        }
        delay(2000)
        snackbarJob.cancel()
    }
}

@Composable
fun BoxScope.Snackbar() {
    snackbarHostState = remember { SnackbarHostState() }
    val keyboardHeight = rememberKeyboardHeight()

    SnackbarHost(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = if (keyboardHeight > 0.dp) keyboardHeight + 56.dp else 56.dp
            )
            .align(Alignment.BottomCenter),
        hostState = snackbarHostState
    ) { data ->
        Snackbar(message = data.visuals.message)
    }
}

@Composable
fun rememberKeyboardHeight(): Dp {
    val view = LocalView.current
    val density = LocalDensity.current
    var keyboardHeight by remember { mutableIntStateOf(0) }

    DisposableEffect(view) {
        val rootView = view.rootView
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardHeight = if (keypadHeight > screenHeight * 0.15) keypadHeight else 0
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return with(density) { keyboardHeight.toDp() }
}

@Composable
private fun Snackbar(message: String) {
    Box(
        modifier = Modifier
            .background(
                color = gray400.copy(alpha = 0.9f),
                shape = RoundedCornerShape(8.dp)
            )
            .heightIn(min = 56.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            color = AppTheme.colors.onPrimary,
            style = body1(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SnackbarPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(message = "스낵바 메세지1\n스낵바 메세지2")
        }
    }
}
