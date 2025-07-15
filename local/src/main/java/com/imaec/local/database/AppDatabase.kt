package com.imaec.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imaec.data.model.local.DiaryEntity
import com.imaec.local.converter.DiaryConverters
import com.imaec.local.dao.DiaryDao

private const val APP_DATABASE_NAME = "app_database"

@Database(
    entities = [DiaryEntity::class],
    version = 2
)
@TypeConverters(DiaryConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    APP_DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                this.instance = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    sql = "ALTER TABLE diary_table ADD COLUMN isLiked INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}
