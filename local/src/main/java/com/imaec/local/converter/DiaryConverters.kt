package com.imaec.local.converter

import androidx.room.TypeConverter
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType

class DiaryConverters {

    @TypeConverter
    fun fromWeatherType(value: WeatherType): String = value.name

    @TypeConverter
    fun toWeatherType(value: String): WeatherType = WeatherType.valueOf(value)

    @TypeConverter
    fun fromMoodType(value: MoodType): String = value.name

    @TypeConverter
    fun toMoodType(value: String): MoodType = MoodType.valueOf(value)
}
