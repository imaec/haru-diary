package com.imaec.harudiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.feature.main.MainScreen

@Composable
fun AppNavGraph() {
    val appNavigator = LocalAppNavigator.current
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        appNavigator.initNavController(navController)
    }

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = AppRoute.Main
        ) {
            composable<AppRoute.Main> {
                MainScreen()
            }
        }
    }
}
