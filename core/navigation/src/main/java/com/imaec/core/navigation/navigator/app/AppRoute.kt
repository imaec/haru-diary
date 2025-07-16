package com.imaec.core.navigation.navigator.app

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object Main : AppRoute

    @Serializable
    data class Write(val diaryId: Long? = null) : AppRoute
}
