package com.imaec.data.datasource.local

import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.model.setting.LanguageType
import kotlinx.coroutines.flow.Flow

interface SettingLocalDataSource {

    suspend fun saveFontType(fontType: FontType)

    fun getFontType(): Flow<FontType>

    suspend fun saveNotificationOn(isNotificationOn: Boolean)

    fun isNotificationOn(): Flow<Boolean>

    suspend fun saveNotificationTime(time: String)

    fun getNotificationTime(): Flow<String>

    suspend fun saveLockOn(isLockOn: Boolean)

    fun isLockOn(): Flow<Boolean>

    suspend fun saveFingerPrintOn(isFingerPrintOn: Boolean)

    fun isFingerPrintOn(): Flow<Boolean>

    suspend fun savePassword(password: String)

    suspend fun getPassword(): String

    suspend fun saveDarkModeType(darkModeType: DarkModeType)

    fun getDarkModeType(): Flow<DarkModeType>

    suspend fun saveLanguageType(languageType: LanguageType)

    fun getLanguageType(): Flow<LanguageType>
}
