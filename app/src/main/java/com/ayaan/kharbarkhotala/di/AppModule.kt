package com.ayaan.kharbarkhotala.di

import android.app.Application
import com.ayaan.kharbarkhotala.data.manager.LocalUserManagerImpl
import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import com.ayaan.kharbarkhotala.domain.usecases.AppEntryUseCase
import com.ayaan.kharbarkhotala.domain.usecases.ReadAppEntry
import com.ayaan.kharbarkhotala.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager{
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager:LocalUserManager
    ) :AppEntryUseCase {
        return AppEntryUseCase(
            saveAppEntry = SaveAppEntry(localUserManager),
            readAppEntry = ReadAppEntry(localUserManager)
        )
    }
}