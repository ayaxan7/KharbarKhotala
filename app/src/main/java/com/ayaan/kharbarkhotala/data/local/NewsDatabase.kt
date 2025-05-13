package com.ayaan.kharbarkhotala.data.local

import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ayaan.kharbarkhotala.domain.model.Article

@Database(entities = [Article::class],version = 1, exportSchema = false)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao

}