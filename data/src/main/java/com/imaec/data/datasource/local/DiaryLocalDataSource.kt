package com.imaec.data.datasource.local

import com.imaec.data.model.local.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface DiaryLocalDataSource {

    suspend fun writeDiary(diary: DiaryEntity): Long

    fun getDiaries(): Flow<List<DiaryEntity>>

    fun getLikedDiaries(): Flow<List<DiaryEntity>>

    fun getDiariesByMonth(month: String): Flow<List<DiaryEntity>>

    suspend fun getDiaryById(id: Long): DiaryEntity?

    fun getDiariesByDate(date: String): List<DiaryEntity>

    fun getDiariesCount(): Flow<Int>

    fun getLikedDiariesCount(): Flow<Int>

    suspend fun updateDiary(diary: DiaryEntity): Int

    suspend fun deleteDiaryById(id: Long): Int

    suspend fun deleteAllDiary(): Int
}
