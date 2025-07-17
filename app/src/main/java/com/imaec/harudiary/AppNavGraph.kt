package com.imaec.harudiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.core.designsystem.component.snackbar.Snackbar
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.feature.diarylist.DiaryListScreen
import com.imaec.feature.main.MainScreen
import com.imaec.feature.write.WriteScreen

@Composable
fun AppNavGraph() {
    val appNavigator = LocalAppNavigator.current
    val navController = rememberNavController()
    var isNavControllerInit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        appNavigator.initNavController(navController)
        isNavControllerInit = true
    }

    if (!isNavControllerInit) return

    AppTheme {
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
        }
        Snackbar()
    }
}
