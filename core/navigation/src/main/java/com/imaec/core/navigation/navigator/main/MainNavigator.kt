package com.imaec.core.navigation.navigator.main

import androidx.navigation.NavOptionsBuilder
import com.imaec.core.navigation.navigator.Navigator

class MainNavigator : Navigator<MainRoute>() {

    override fun navigate(route: MainRoute, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        if (isCurrentRoute(route)) return

        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(route) { inclusive = false }
        }
    }

    private fun isCurrentRoute(route: MainRoute) =
        navController.currentBackStackEntry?.destination?.route == route.javaClass.canonicalName
}
