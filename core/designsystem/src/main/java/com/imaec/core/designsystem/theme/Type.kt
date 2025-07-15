package com.imaec.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object AppFont {
    private var fontFamily: FontFamily = pretendard

    fun getFontFamily(): FontFamily = fontFamily

    fun setFontFamily(fontFamily: FontFamily) {
        this.fontFamily = fontFamily
    }

    val typography = Typography().run {
        copy(
            displayLarge = displayLarge.copy(fontFamily = fontFamily),
            displayMedium = displayMedium.copy(fontFamily = fontFamily),
            displaySmall = displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = titleLarge.copy(fontFamily = fontFamily),
            titleMedium = titleMedium.copy(fontFamily = fontFamily),
            titleSmall = titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = bodySmall.copy(fontFamily = fontFamily),
            labelLarge = labelLarge.copy(fontFamily = fontFamily),
            labelMedium = labelMedium.copy(fontFamily = fontFamily),
            labelSmall = labelSmall.copy(fontFamily = fontFamily)
        )
    }
}

@Composable
private fun textStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight
): TextStyle = TextStyle(
    color = AppTheme.colors.onBackground,
    fontSize = fontSize,
    fontWeight = fontWeight,
    fontFamily = AppFont.getFontFamily(),
    lineHeight = fontSize * 1.2
)

@Composable
fun display1() = textStyle(
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun display2() = textStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun display3() = textStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun h1() = textStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun h2() = textStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.SemiBold
)

@Composable
fun h3() = textStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.SemiBold
)

@Composable
fun body1() = textStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun body2() = textStyle(
    fontSize = 15.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun label1() = textStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun label2() = textStyle(
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun caption1() = textStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun caption2() = textStyle(
    fontSize = 11.sp,
    fontWeight = FontWeight.Medium
)

@Composable
fun caption3() = textStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Medium
)
