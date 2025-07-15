package com.imaec.domain.repository

import com.imaec.domain.model.diary.DiaryDto
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {

    suspend fun writeDiary(diary: DiaryDto): Long

    fun getDiaries(): Flow<List<DiaryDto>>

    fun getLikedDiaries(): Flow<List<DiaryDto>>

    fun getDiariesByMonth(month: String): Flow<List<DiaryDto>>

    suspend fun getDiaryById(id: Long): DiaryDto?

    fun getDiariesByDate(date: String): List<DiaryDto>

    fun getDiariesCount(): Flow<Int>

    fun getLikedDiariesCount(): Flow<Int>

    suspend fun updateDiary(diary: DiaryDto): Int

    suspend fun deleteDiaryById(id: Long): Int

    suspend fun deleteAllDiary(): Int
}
