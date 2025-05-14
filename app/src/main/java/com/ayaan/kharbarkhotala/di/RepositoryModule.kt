package com.ayaan.kharbarkhotala.di

import com.ayaan.kharbarkhotala.data.repository.NewsRepositoryImpl
import com.ayaan.kharbarkhotala.data.repository.TrendingNewsRepositoryImpl
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import com.ayaan.kharbarkhotala.domain.repository.TrendingNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    @Singleton
    abstract fun bindTrendingNewsRepository(trendingNewsRepositoryImpl: TrendingNewsRepositoryImpl): TrendingNewsRepository
}