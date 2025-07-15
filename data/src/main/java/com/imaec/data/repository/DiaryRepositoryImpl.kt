package com.imaec.data.repository

import com.imaec.data.datasource.local.DiaryLocalDataSource
import com.imaec.data.model.local.DiaryEntity
import com.imaec.data.model.local.toDto
import com.imaec.data.model.local.toEntity
import com.imaec.domain.model.diary.DiaryDto
import com.imaec.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryLocalDataSource: DiaryLocalDataSource
) : DiaryRepository {

    override suspend fun writeDiary(diary: DiaryDto): Long =
        diaryLocalDataSource.writeDiary(diary.toEntity())

    override fun getDiaries(): Flow<List<DiaryDto>> =
        diaryLocalDataSource.getDiaries().map { it.map(DiaryEntity::toDto) }

    override fun getLikedDiaries(): Flow<List<DiaryDto>> =
        diaryLocalDataSource.getLikedDiaries().map { it.map(DiaryEntity::toDto) }

    override fun getDiariesByMonth(month: String): Flow<List<DiaryDto>> =
        diaryLocalDataSource.getDiariesByMonth(month).map { it.map(DiaryEntity::toDto) }

    override suspend fun getDiaryById(id: Long): DiaryDto? =
        diaryLocalDataSource.getDiaryById(id)?.toDto()

    override fun getDiariesByDate(date: String): List<DiaryDto> =
        diaryLocalDataSource.getDiariesByDate(date).map(DiaryEntity::toDto)

    override fun getDiariesCount(): Flow<Int> = diaryLocalDataSource.getDiariesCount()

    override fun getLikedDiariesCount(): Flow<Int> = diaryLocalDataSource.getLikedDiariesCount()

    override suspend fun updateDiary(diary: DiaryDto): Int =
        diaryLocalDataSource.updateDiary(diary.toEntity())

    override suspend fun deleteDiaryById(id: Long): Int = diaryLocalDataSource.deleteDiaryById(id)

    override suspend fun deleteAllDiary(): Int = diaryLocalDataSource.deleteAllDiary()
}
