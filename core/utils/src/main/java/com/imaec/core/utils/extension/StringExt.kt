package com.imaec.core.utils.extension

import java.util.Locale

fun String.from(vararg args: Any?) = String.format(
    locale = Locale.getDefault(),
    format = this,
    args = args
)
