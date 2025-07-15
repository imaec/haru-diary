package com.imaec.local.datasource

import com.imaec.data.datasource.local.DiaryLocalDataSource
import com.imaec.data.model.local.DiaryEntity
import com.imaec.local.dao.DiaryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryLocalDataSourceImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryLocalDataSource {

    override suspend fun writeDiary(diary: DiaryEntity): Long = diaryDao.insertDiary(diary)

    override fun getDiaries(): Flow<List<DiaryEntity>> = diaryDao.getDiaries()

    override fun getLikedDiaries(): Flow<List<DiaryEntity>> = diaryDao.getLikedDiaries()

    override fun getDiariesByMonth(month: String): Flow<List<DiaryEntity>> =
        diaryDao.getDiariesByMonth(month)

    override suspend fun getDiaryById(id: Long): DiaryEntity? = diaryDao.getDiaryById(id)

    override fun getDiariesByDate(date: String): List<DiaryEntity> = diaryDao.getDiariesByDate(date)

    override fun getDiariesCount(): Flow<Int> = diaryDao.getDiariesCount()

    override fun getLikedDiariesCount(): Flow<Int> = diaryDao.getLikedDiariesCount()

    override suspend fun updateDiary(diary: DiaryEntity): Int = diaryDao.updateDiary(diary)

    override suspend fun deleteDiaryById(id: Long): Int = diaryDao.deleteDiaryById(id)

    override suspend fun deleteAllDiary(): Int = diaryDao.deleteAllDiary()
}
