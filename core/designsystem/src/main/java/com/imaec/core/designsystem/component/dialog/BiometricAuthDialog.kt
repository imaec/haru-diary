package com.imaec.core.designsystem.component.dialog

import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.imaec.core.utils.extension.findActivity

@Composable
fun BiometricAuthDialog(
    onAuthenticated: () -> Unit,
    onFailed: () -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    LaunchedEffect(Unit) {
        val executor = ContextCompat.getMainExecutor(context)
        val prompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    onAuthenticated()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onFailed()
                }

                override fun onAuthenticationFailed() {
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("생체 인증")
            .setSubtitle("본인 확인을 위해 생체 인증을 해주세요.")
            .setNegativeButtonText("취소")
            .build()

        prompt.authenticate(promptInfo)
    }
}
