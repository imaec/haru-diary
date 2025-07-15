package com.imaec.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

private val LocalColors = compositionLocalOf<ColorScheme> {
    error("No colors provided!")
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: ColorScheme = if (darkTheme) {
        AppColors.defaultDarkColors()
    } else {
        AppColors.defaultLightColors()
    },
    typography: Typography = AppFont.typography,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            content = {
                Box { content() }
            }
        )
    }
}

object AppTheme {

    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}
