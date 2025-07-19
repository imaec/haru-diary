package com.imaec.domain.model.setting

enum class LanguageType(val language: String) {
    SYSTEM(language = ""),
    KR(language = "ko"),
    US(language = "en");

    companion object {
        fun fromString(name: String?): LanguageType? = LanguageType.entries.firstOrNull {
            it.name == name
        }
    }
}
