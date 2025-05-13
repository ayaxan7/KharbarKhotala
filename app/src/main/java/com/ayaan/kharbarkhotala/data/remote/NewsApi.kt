package com.ayaan.kharbarkhotala.data.remote

import com.ayaan.kharbarkhotala.BuildConfig.NEWS_API_KEY
import com.ayaan.kharbarkhotala.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String=NEWS_API_KEY
    ): NewsResponse
    @GET("everything")
    suspend fun searchNews(
        @Query("page") page: Int,
        @Query("q") query: String,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String=NEWS_API_KEY
    ): NewsResponse
}