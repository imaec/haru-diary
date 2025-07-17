package com.imaec.core.navigation.navigator.app

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object Main : AppRoute

    @Serializable
    data class Write(val diaryId: Long? = null) : AppRoute

    @Serializable
    data object DiaryList : AppRoute

    @Serializable
    data object LikedDiaryList : AppRoute

    @Serializable
    data object FontSetting : AppRoute

    @Serializable
    data object NotificationSetting : AppRoute

    @Serializable
    data object LockSetting : AppRoute

    @Serializable
    data object DarkModeSetting : AppRoute

    @Serializable
    data object BackupSetting : AppRoute

    @Serializable
    data object LanguageSetting : AppRoute

    @Serializable
    data object AppReview : AppRoute

    @Serializable
    data object Cs : AppRoute

    @Serializable
    data class Password(val passwordType: String) : AppRoute
}
