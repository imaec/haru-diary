package com.imaec.data.repository

import com.imaec.data.datasource.local.SettingLocalDataSource
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.model.setting.LanguageType
import com.imaec.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingLocalDataSource: SettingLocalDataSource
) : SettingRepository {

    override suspend fun saveFontType(fontType: FontType) {
        settingLocalDataSource.saveFontType(fontType = fontType)
    }

    override fun getFontType(): Flow<FontType> = settingLocalDataSource.getFontType()

    override suspend fun saveNotificationOn(isNotificationOn: Boolean) {
        settingLocalDataSource.saveNotificationOn(isNotificationOn = isNotificationOn)
    }

    override fun isNotificationOn(): Flow<Boolean> = settingLocalDataSource.isNotificationOn()

    override suspend fun saveNotificationTime(time: String) {
        settingLocalDataSource.saveNotificationTime(time = time)
    }

    override fun getNotificationTime(): Flow<String> = settingLocalDataSource.getNotificationTime()

    override suspend fun saveLockOn(isLockOn: Boolean) {
        settingLocalDataSource.saveLockOn(isLockOn = isLockOn)
    }

    override fun isLockOn(): Flow<Boolean> = settingLocalDataSource.isLockOn()

    override suspend fun saveFingerPrintOn(isFingerPrintOn: Boolean) {
        settingLocalDataSource.saveFingerPrintOn(isFingerPrintOn = isFingerPrintOn)
    }

    override fun isFingerPrintOn(): Flow<Boolean> = settingLocalDataSource.isFingerPrintOn()

    override suspend fun savePassword(password: String) {
        settingLocalDataSource.savePassword(password = password)
    }

    override suspend fun getPassword(): String = settingLocalDataSource.getPassword()

    override suspend fun saveDarkModeType(darkModeType: DarkModeType) {
        settingLocalDataSource.saveDarkModeType(darkModeType = darkModeType)
    }

    override fun getDarkModeType(): Flow<DarkModeType> = settingLocalDataSource.getDarkModeType()

    override suspend fun saveLanguageType(languageType: LanguageType) {
        settingLocalDataSource.saveLanguageType(languageType = languageType)
    }

    override fun getLanguageType(): Flow<LanguageType> = settingLocalDataSource.getLanguageType()
}
