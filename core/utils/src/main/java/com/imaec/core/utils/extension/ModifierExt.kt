package com.imaec.core.utils.extension

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

fun Modifier.singleClickable(
    enabled: Boolean = true,
    debounceTime: Long = 500L,
    hasRipple: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var isClickable by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val localIndication = LocalIndication.current

    LaunchedEffect(isClickable) {
        if (!isClickable) {
            delay(debounceTime)
            isClickable = true
        }
    }

    this.clickable(
        enabled = enabled && isClickable,
        onClick = {
            if (isClickable) {
                isClickable = false
                onClick()
            }
        },
        interactionSource = interactionSource,
        indication = if (hasRipple) localIndication else null
    )
}
