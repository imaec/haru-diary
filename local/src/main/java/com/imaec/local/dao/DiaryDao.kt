package com.imaec.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.imaec.data.model.local.DiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryEntity): Long

    @Query("SELECT * FROM diary_table ORDER BY date DESC, id DESC")
    fun getDiaries(): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_table WHERE isLiked = 1 ORDER BY date DESC, id DESC")
    fun getLikedDiaries(): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_table WHERE date LIKE :month || '%' ORDER BY date ASC")
    fun getDiariesByMonth(month: String): Flow<List<DiaryEntity>>

    @Query("SELECT * FROM diary_table WHERE date = :date")
    fun getDiariesByDate(date: String): List<DiaryEntity>

    @Query("SELECT * FROM diary_table WHERE id = :id")
    suspend fun getDiaryById(id: Long): DiaryEntity?

    @Query("SELECT count(*) FROM diary_table")
    fun getDiariesCount(): Flow<Int>

    @Query("SELECT count(*) FROM diary_table WHERE isLiked = 1")
    fun getLikedDiariesCount(): Flow<Int>

    @Update
    suspend fun updateDiary(diary: DiaryEntity): Int

    @Query("DELETE FROM diary_table WHERE id = :id")
    suspend fun deleteDiaryById(id: Long): Int

    @Query("DELETE FROM diary_table")
    suspend fun deleteAllDiary(): Int
}
