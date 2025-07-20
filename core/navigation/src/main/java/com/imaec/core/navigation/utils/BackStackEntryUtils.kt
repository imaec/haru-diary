package com.imaec.core.navigation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.imaec.core.navigation.navigator.Navigator

const val WRITE_DIARY_MESSAGE_KEY = "writeDiaryMessage"
const val DIARY_ID = "diaryId"
const val PASSWORD_TYPE = "passwordType"

@Composable
fun <T> BackStackEntryCallback(
    navigator: Navigator<*>,
    key: String,
    onResult: suspend (result: T) -> Unit
) {
    val entry = navigator.navController.currentBackStackEntry

    LaunchedEffect(entry) {
        entry?.lifecycle?.repeatOnLifecycle(Lifecycle.State.STARTED) {
            val savedStateHandle = entry.savedStateHandle
            savedStateHandle
                .getStateFlow<T?>(key, null)
                .collect { result ->
                    result?.let {
                        onResult(result)
                        savedStateHandle[key] = null
                    }
                }
        }
    }
}
