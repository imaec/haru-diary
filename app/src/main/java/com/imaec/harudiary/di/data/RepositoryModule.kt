package com.imaec.harudiary.di.data

import com.imaec.data.repository.DiaryRepositoryImpl
import com.imaec.data.repository.SettingRepositoryImpl
import com.imaec.domain.repository.DiaryRepository
import com.imaec.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository
}
