package com.imaec.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val white: Color = Color(0xFFFFFFFF)
val gray25: Color = Color(0xFFFBFBFB)
val gray50:Color = Color(0xFFF8F8F8)
val gray100:Color = Color(0xFFD9D9D9)
val gray200:Color = Color(0xFFC0C0C0)
val gray300:Color = Color(0xFFA6A6A6)
val gray400:Color = Color(0xFF8C8C8C)
val gray500:Color = Color(0xFF737373)
val gray600:Color = Color(0xFF595959)
val gray700:Color = Color(0xFF404040)
val gray800: Color = Color(0xFF262626)
val gray900: Color = Color(0xFF1A1A1A)
val black: Color = Color(0xFF000000)
val primary50: Color = Color(0xFFF6F2EE)
val primary100: Color = Color(0xFFEDE3D7)
val primary200: Color = Color(0xFFE5D6C8)
val primary300: Color = Color(0xFFDCCEC3)
val primary400: Color = Color(0xFFCBB8AD)
val primary500: Color = Color(0xFFB8A294)
val primary600: Color = Color(0xFFA38B7A)
val primary700: Color = Color(0xFF8E7563)
val primary800: Color = Color(0xFF776051)
val primary900: Color = Color(0xFF5F4A3D)
val error: Color = Color(0xFFD53A47)
val like: Color = Color(0xFFFF5F62)

@Immutable
object AppColors {

    @Composable
    fun defaultLightColors() = lightColorScheme(
        primary = primary500, // 주요 UI 색상 (브랜드 컬러 등). 버튼, 토글, 활성 탭 등 핵심 액션 요소에 사용
        onPrimary = white, // primary 배경 위에 놓일 텍스트/아이콘 색 (ex. 흰색 버튼 텍스트 등)
        primaryContainer = primary100, // primary보다 넓은 영역에 사용되는 배경 색 (ex. 카드 배경, 강조 블록 등)
        onPrimaryContainer = primary400, // primaryContainer 위 텍스트/아이콘 색
//        secondary: Color, // 보조 색상. primary와 조합되는 UI 요소에 사용 (ex. 선택된 상태, 보조 버튼)
//        onSecondary: Color, // secondary 위의 텍스트/아이콘 색
//        secondaryContainer: Color, // 넓은 배경 영역에서 secondary 톤을 사용하는 색상
//        onSecondaryContainer: Color, // secondaryContainer 위의 텍스트/아이콘 색
        tertiary = primary800, // 강조색 또는 고유 기능 색상. 예: 사진, 차트 등 시각 요소
//        onTertiary: Color, // tertiary 위의 텍스트/아이콘 색
//        tertiaryContainer: Color, // tertiary 배경 컨테이너용 색상
//        onTertiaryContainer: Color, // tertiaryContainer 위의 콘텐츠 색
        background = gray50, // 전체 배경 색상 (스크린 전체의 기본 배경)
        onBackground = gray800, // background 위 텍스트/아이콘 색
        surface = white, // 카드, 시트, 대화상자 등 surface용 색상
        onSurface = gray800, // surface 위 콘텐츠 색상 (텍스트, 아이콘 등)
        error = error, // 오류 메시지, 입력 오류 등 강조할 때 사용
        onError = white, // error 위 텍스트/아이콘 색
//        errorContainer: Color, // 넓은 영역에서 오류를 나타내는 배경 색
//        onErrorContainer: Color, // errorContainer 위 콘텐츠 색상
        outline = gray100, // 구분선 또는 경계선 색상 (ex. 텍스트 필드 경계선)
//        outlineVariant: Color, // 더 미세한 구분선에 사용 (덜 강조됨)
    )

    @Composable
    fun defaultDarkColors() = darkColorScheme(
        primary = primary300, // 주요 UI 색상 (브랜드 컬러 등). 버튼, 토글, 활성 탭 등 핵심 액션 요소에 사용
        onPrimary = gray600, // primary 배경 위에 놓일 텍스트/아이콘 색 (ex. 흰색 버튼 텍스트 등)
        primaryContainer = primary400, // primary보다 넓은 영역에 사용되는 배경 색 (ex. 카드 배경, 강조 블록 등)
        onPrimaryContainer = gray500, // primaryContainer 위 텍스트/아이콘 색
//        secondary: Color, // 보조 색상. primary와 조합되는 UI 요소에 사용 (ex. 선택된 상태, 보조 버튼)
//        onSecondary: Color, // secondary 위의 텍스트/아이콘 색
//        secondaryContainer: Color, // 넓은 배경 영역에서 secondary 톤을 사용하는 색상
//        onSecondaryContainer: Color, // secondaryContainer 위의 텍스트/아이콘 색
        tertiary = primary600, // 강조색 또는 고유 기능 색상. 예: 사진, 차트 등 시각 요소
//        onTertiary: Color, // tertiary 위의 텍스트/아이콘 색
//        tertiaryContainer: Color, // tertiary 배경 컨테이너용 색상
//        onTertiaryContainer: Color, // tertiaryContainer 위의 콘텐츠 색
        background = gray800, // 전체 배경 색상 (스크린 전체의 기본 배경)
        onBackground = gray50, // background 위 텍스트/아이콘 색
        surface = gray700, // 카드, 시트, 대화상자 등 surface용 색상
        onSurface = gray50, // surface 위 콘텐츠 색상 (텍스트, 아이콘 등)
        error = error, // 오류 메시지, 입력 오류 등 강조할 때 사용
        onError = white, // error 위 텍스트/아이콘 색
//        errorContainer: Color, // 넓은 영역에서 오류를 나타내는 배경 색
//        onErrorContainer: Color, // errorContainer 위 콘텐츠 색상
        outline = gray100, // 구분선 또는 경계선 색상 (ex. 텍스트 필드 경계선)
//        outlineVariant: Color, // 더 미세한 구분선에 사용 (덜 강조됨)
    )
}
