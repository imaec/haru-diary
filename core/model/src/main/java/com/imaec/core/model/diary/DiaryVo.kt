package com.imaec.core.model.diary

import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType

data class DiaryVo(
    val id: Long = 0L,
    val date: String,
    val weather: WeatherType,
    val mood: MoodType,
    val content: String,
    val isLiked: Boolean
) {

    companion object {
        fun fromDto(dto: DiaryDto): DiaryVo = DiaryVo(
            id = dto.id,
            date = dto.date,
            weather = dto.weather,
            mood = dto.mood,
            content = dto.content,
            isLiked = dto.isLiked
        )

        fun toDto(vo: DiaryVo): DiaryDto = DiaryDto(
            id = vo.id,
            date = vo.date,
            weather = vo.weather,
            mood = vo.mood,
            content = vo.content,
            isLiked = vo.isLiked
        )

        fun mock(content: String? = null) = DiaryVo(
            date = "2025-06-19",
            mood = MoodType.HAPPY,
            weather = WeatherType.RAINY,
            content = content ?: run {
                "힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다" +
                    "힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다"
            },
            isLiked = true
        )

        fun mockList(size: Int = 10) = List(size) {
            mock()
        }
    }
}
