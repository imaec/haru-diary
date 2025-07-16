package com.imaec.feature.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.primary500
import com.imaec.core.designsystem.theme.primary800
import com.imaec.core.designsystem.theme.white
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.LocalMainNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.core.navigation.navigator.main.MainRoute
import com.imaec.core.resource.R
import com.imaec.feature.home.HomeScreen
import com.imaec.feature.my.MyScreen

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        selectedRoute = uiState.selectedRoute,
        onBottomBarItemClick = viewModel::selectBottomBarItem
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainScreen(
    selectedRoute: MainRoute,
    onBottomBarItemClick: (MainRoute) -> Unit
) {
    val mainNavigator = LocalMainNavigator.current
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        mainNavigator.initNavController(navController)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { _ ->
            NavHost(
                navController = navController,
                startDestination = MainRoute.Home
            ) {
                composable<MainRoute.Home> {
                    HomeScreen()
                }
                composable<MainRoute.My> {
                    MyScreen()
                }
            }
        },
        floatingActionButton = {
            MainFloatingButton()
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            MainBottomBar(
                selectedRoute = selectedRoute,
                onBottomBarItemClick = onBottomBarItemClick
            )
        }
    )
}

@Composable
private fun MainFloatingButton() {
    val appNavigator = LocalAppNavigator.current

    FloatingActionButton(
        onClick = {
            appNavigator.navigate(AppRoute.Write())
        },
        containerColor = Color.Transparent,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        modifier = Modifier
            .offset(y = 32.dp)
            .background(
                brush = Brush.linearGradient(colors = listOf(primary500, primary800)),
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_write),
            tint = white,
            contentDescription = null
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen(
            selectedRoute = MainRoute.Home,
            onBottomBarItemClick = {}
        )
    }
}
