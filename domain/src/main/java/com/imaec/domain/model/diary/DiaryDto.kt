package com.imaec.domain.model.diary

import kotlinx.serialization.Serializable

@Serializable
data class DiaryDto(
    val id: Long = 0L,
    val date: String,
    val weather: WeatherType,
    val mood: MoodType,
    val content: String,
    val isLiked: Boolean
)
