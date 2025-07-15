package com.imaec.harudiary.di.core

import com.imaec.core.navigation.navigator.app.AppNavigator
import com.imaec.core.navigation.navigator.main.MainNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NavigationModule {

    @Provides
    @Singleton
    fun provideAppNavigator() = AppNavigator()

    @Provides
    @Singleton
    fun provideMainNavigator() = MainNavigator()
}
