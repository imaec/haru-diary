package com.imaec.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType

@Entity(tableName = "diary_table")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: String,
    val weather: WeatherType,
    val mood: MoodType,
    val content: String,
    val isLiked: Boolean = false
)

fun DiaryEntity.toDto() = DiaryDto(
    id = id,
    date = date,
    weather = weather,
    mood = mood,
    content = content,
    isLiked = isLiked
)

fun DiaryDto.toEntity() = DiaryEntity(
    id = id,
    date = date,
    weather = weather,
    mood = mood,
    content = content,
    isLiked = isLiked
)
