package com.imaec.harudiary

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.core.designsystem.component.snackbar.Snackbar
import com.imaec.core.designsystem.theme.AppFont
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.kotra_hope
import com.imaec.core.designsystem.theme.leeseoyun
import com.imaec.core.designsystem.theme.ownglyph_parckdahyun
import com.imaec.core.designsystem.theme.ownglyph_ryurue
import com.imaec.core.designsystem.theme.pretendard
import com.imaec.core.model.setting.PasswordType
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.core.utils.utils.NotificationPermissionRequest
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.model.setting.FontType
import com.imaec.feature.darkmodesetting.DarkModeSettingScreen
import com.imaec.feature.diarylist.DiaryListScreen
import com.imaec.feature.fontsetting.FontSettingScreen
import com.imaec.feature.languagesetting.LanguageSettingScreen
import com.imaec.feature.likeddiarylist.LikedDiaryListScreen
import com.imaec.feature.locksetting.LockSettingScreen
import com.imaec.feature.main.MainScreen
import com.imaec.feature.notificationsetting.NotificationSettingScreen
import com.imaec.feature.password.PasswordScreen
import com.imaec.feature.write.WriteScreen

@Composable
fun AppNavGraph(viewModel: AppViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val appNavigator = LocalAppNavigator.current
    val navController = rememberNavController()
    var isNavControllerInit by remember { mutableStateOf(false) }
    val typography = rememberTypography(uiState.fontType)

    LaunchedEffect(Unit) {
        appNavigator.initNavController(navController)
        isNavControllerInit = true
    }

    LockScreenNavigate(uiState = uiState)

    if (!isNavControllerInit) return

    NotificationPermissionRequest()

    AppTheme(
        darkTheme = when (uiState.darkModeType) {
            DarkModeType.SYSTEM -> isSystemInDarkTheme()
            DarkModeType.LIGHT -> false
            DarkModeType.DARK -> true
        },
        typography = typography
    ) {
        NavHost(
            navController = navController,
            startDestination = AppRoute.Main
        ) {
            composable<AppRoute.Main> {
                MainScreen()
            }
            composable<AppRoute.Write> {
                WriteScreen()
            }
            composable<AppRoute.DiaryList> {
                DiaryListScreen()
            }
            composable<AppRoute.LikedDiaryList> {
                LikedDiaryListScreen()
            }
            composable<AppRoute.FontSetting> {
                FontSettingScreen()
            }
            composable<AppRoute.NotificationSetting> {
                NotificationSettingScreen()
            }
            composable<AppRoute.LockSetting> {
                LockSettingScreen()
            }
            composable<AppRoute.Password> {
                PasswordScreen()
            }
            composable<AppRoute.DarkModeSetting> {
                DarkModeSettingScreen()
            }
            composable<AppRoute.LanguageSetting> {
                LanguageSettingScreen()
            }
        }
        Snackbar()
    }
}

@Composable
private fun LockScreenNavigate(uiState: AppUiState) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val appNavigator = LocalAppNavigator.current

    DisposableEffect(lifecycleOwner.lifecycle, uiState.isLoading) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && uiState.isLockOn) {
                appNavigator.navigate(
                    route = AppRoute.Password(passwordType = PasswordType.LAUNCH.name),
                    optionsBuilder = {
                        launchSingleTop = true
                    }
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}

@Composable
fun rememberTypography(fontType: FontType): Typography {
    when (fontType) {
        FontType.DEFAULT -> pretendard
        FontType.KOTRA_HOPE -> kotra_hope
        FontType.OWNGLYPH_PARKDAHYUN -> ownglyph_parckdahyun
        FontType.OWNGLYPH_RYURUE -> ownglyph_ryurue
        FontType.LEESEOYUN -> leeseoyun
    }.also {
        if (AppFont.getFontFamily() != it) {
            AppFont.setFontFamily(fontFamily = it)
        }
    }
    return remember(fontType) { AppFont.typography }
}
