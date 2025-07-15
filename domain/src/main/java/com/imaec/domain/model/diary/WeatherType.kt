package com.imaec.domain.model.diary

enum class WeatherType(val emoji: String) {
    SUNNY(emoji = "☀️"),
    PARTLY_CLOUDY(emoji = "⛅️"),
    CLOUDY(emoji = "☁️"),
    RAINY(emoji = "🌧️"),
    SNOWY(emoji = "🌨️");

    companion object {
        fun toSelectableList(): List<Pair<WeatherType, Boolean>> =
            WeatherType.entries.mapIndexed { index, weatherType ->
                weatherType to (index == 0)
            }
    }
}
