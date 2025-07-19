package com.imaec.domain.model.setting

enum class DarkModeType {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun fromString(name: String): DarkModeType? = DarkModeType.entries.firstOrNull {
            it.name == name
        }
    }
}
