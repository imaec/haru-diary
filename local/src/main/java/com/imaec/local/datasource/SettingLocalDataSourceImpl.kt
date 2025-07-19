package com.imaec.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.imaec.data.datasource.local.SettingLocalDataSource
import com.imaec.domain.model.setting.DarkModeType
import com.imaec.domain.model.setting.FontType
import com.imaec.domain.model.setting.LanguageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingLocalDataSource {

    override suspend fun saveFontType(fontType: FontType) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("fontType")] = fontType.name
        }
    }

    override fun getFontType(): Flow<FontType> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("fontType")]?.let {
            FontType.fromString(it)
        } ?: FontType.DEFAULT
    }

    override suspend fun saveNotificationOn(isNotificationOn: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey("isNotificationOn")] = isNotificationOn
        }
    }

    override fun isNotificationOn(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey("isNotificationOn")] ?: true
    }

    override suspend fun saveNotificationTime(time: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("notificationTime")] = time
        }
    }

    override fun getNotificationTime(): Flow<String> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("notificationTime")] ?: "오후 9:00"
    }

    override suspend fun saveLockOn(isLockOn: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey("isLockOn")] = isLockOn
        }
    }

    override fun isLockOn(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey("isLockOn")] ?: false
    }

    override suspend fun saveFingerPrintOn(isFingerPrintOn: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey("isFingerPrintOn")] = isFingerPrintOn
        }
    }

    override fun isFingerPrintOn(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey("isFingerPrintOn")] ?: false
    }

    override suspend fun savePassword(password: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("password")] = password
        }
    }

    override suspend fun getPassword(): String = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("password")] ?: ""
    }.first()

    override suspend fun saveDarkModeType(darkModeType: DarkModeType) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("darkModeType")] = darkModeType.name
        }
    }

    override fun getDarkModeType(): Flow<DarkModeType> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("darkModeType")]?.let {
            DarkModeType.fromString(it)
        } ?: DarkModeType.SYSTEM
    }

    override suspend fun saveLanguageType(languageType: LanguageType) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("languageType")] = languageType.name
        }
    }

    override fun getLanguageType(): Flow<LanguageType> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("languageType")]?.let {
            LanguageType.fromString(it)
        } ?: LanguageType.SYSTEM
    }
}
