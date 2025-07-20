package com.imaec.core.model.setting

enum class PasswordType {
    NEW,
    UPDATE,
    OFF,
    LAUNCH;

    companion object {
        fun fromString(name: String?): PasswordType? = PasswordType.entries.firstOrNull {
            it.name == name
        }
    }
}
