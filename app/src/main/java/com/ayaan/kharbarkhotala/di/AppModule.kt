package com.ayaan.kharbarkhotala.di

import android.app.Application
import com.ayaan.kharbarkhotala.data.manager.LocalUserManagerImpl
import com.ayaan.kharbarkhotala.data.remote.NewsApi
import com.ayaan.kharbarkhotala.data.repository.NewsRepositoryImpl
import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import com.ayaan.kharbarkhotala.domain.usecases.appentry.AppEntryUseCase
import com.ayaan.kharbarkhotala.domain.usecases.appentry.ReadAppEntry
import com.ayaan.kharbarkhotala.domain.usecases.appentry.SaveAppEntry
import com.ayaan.kharbarkhotala.domain.usecases.news.GetNews
import com.ayaan.kharbarkhotala.domain.usecases.news.NewsUseCases
import com.ayaan.kharbarkhotala.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsApi = newsApi
        )
    }

    @Provides
    @Singleton
    fun provideNewsUseCases (
        newsRepository: NewsRepository
    ): NewsUseCases{
        return NewsUseCases(
            getNews=GetNews(
                newsRepository = newsRepository
            )
        )
    }
}