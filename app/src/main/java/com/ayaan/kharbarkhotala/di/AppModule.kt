package com.ayaan.kharbarkhotala.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.data.local.NewsDatabase
import com.ayaan.kharbarkhotala.data.local.NewsTypeConverter
import com.ayaan.kharbarkhotala.data.manager.LocalUserManagerImpl
import com.ayaan.kharbarkhotala.data.remote.NewsApi
import com.ayaan.kharbarkhotala.data.repository.NewsRepositoryImpl
import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import com.ayaan.kharbarkhotala.domain.usecases.appentry.AppEntryUseCase
import com.ayaan.kharbarkhotala.domain.usecases.appentry.ReadAppEntry
import com.ayaan.kharbarkhotala.domain.usecases.appentry.SaveAppEntry
import com.ayaan.kharbarkhotala.domain.usecases.news.DeleteArticle
import com.ayaan.kharbarkhotala.domain.usecases.news.GetNews
import com.ayaan.kharbarkhotala.domain.usecases.news.InsertArticle
import com.ayaan.kharbarkhotala.domain.usecases.news.SearchNews
import com.ayaan.kharbarkhotala.utils.Constants.BASE_URL
import com.ayaan.kharbarkhotala.utils.Constants.NEWS_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application, klass = NewsDatabase::class.java, name = NEWS_DB
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao
}