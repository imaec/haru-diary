package com.imaec.core.navigation.navigator.main

import kotlinx.serialization.Serializable

sealed interface MainRoute {

    @Serializable
    data object Home : MainRoute

    @Serializable
    data object My : MainRoute
}
