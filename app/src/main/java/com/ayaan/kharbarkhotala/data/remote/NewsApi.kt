package com.ayaan.kharbarkhotala.data.remote

import com.ayaan.kharbarkhotala.data.remote.dto.NewsResponse
import com.ayaan.kharbarkhotala.domain.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
//        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
//    ): List<Article>
}