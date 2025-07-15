package com.imaec.harudiary.di.local

import android.content.Context
import com.imaec.local.dao.DiaryDao
import com.imaec.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Provides
    @Singleton
    fun providesRoom(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providesDiaryDao(appDatabase: AppDatabase): DiaryDao = appDatabase.diaryDao()
}
