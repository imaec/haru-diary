package com.imaec.harudiary.di.local

import com.imaec.data.datasource.local.DiaryLocalDataSource
import com.imaec.local.datasource.DiaryLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindDiaryLocalDatasource(
        diaryLocalDataSourceImpl: DiaryLocalDataSourceImpl
    ): DiaryLocalDataSource
}
