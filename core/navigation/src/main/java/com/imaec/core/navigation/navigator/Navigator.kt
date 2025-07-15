package com.imaec.core.navigation.navigator

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.imaec.core.navigation.navigator.app.AppNavigator
import com.imaec.core.navigation.navigator.main.MainNavigator

val LocalAppNavigator = compositionLocalOf {
    AppNavigator()
}

val LocalMainNavigator = compositionLocalOf {
    MainNavigator()
}

abstract class Navigator<T : Any> {
    lateinit var navController: NavController

    open fun initNavController(navController: NavController) {
        this.navController = navController
    }

    open fun navigate(route: T, optionsBuilder: (NavOptionsBuilder.() -> Unit)? = null) {
        val options = optionsBuilder?.let { navOptions(it) }
        navController.navigate(route, options)
    }

    open fun navigateUp() {
        navController.navigateUp()
    }

    open fun popUp(vararg results: Pair<String, Any>) {
        results.forEach { (key, value) ->
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(key, value)
        }
        navController.popBackStack()
    }

    open fun popUpTo(route: T, inclusive: Boolean, vararg results: Pair<String, Any>) {
        results.forEach { (key, value) ->
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(key, value)
        }
        navController.popBackStack(route, inclusive)
    }

    open fun <R> navigateBackWithResult(key: String, result: R, route: T) {
        val backStackEntry = navController.getBackStackEntry(route)
        backStackEntry.savedStateHandle[key] = result

        navController.popBackStack(route, false)
    }
}
