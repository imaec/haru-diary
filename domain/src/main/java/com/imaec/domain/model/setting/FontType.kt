package com.imaec.domain.model.setting

enum class FontType {
    DEFAULT,
    KOTRA_HOPE,
    OWNGLYPH_RYURUE,
    OWNGLYPH_PARKDAHYUN,
    LEESEOYUN;

    companion object {
        fun fromString(name: String?): FontType? = FontType.entries.firstOrNull {
            it.name == name
        }
    }
}
