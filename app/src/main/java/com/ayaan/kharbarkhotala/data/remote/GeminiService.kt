package com.ayaan.kharbarkhotala.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor(
    private val generativeModel: GenerativeModel
) {

    suspend fun summarizeArticle(content: String): Result<String> {
        return try {
            val prompt = """
                Please summarize the following news article in exactly 8-10 lines. 
                The summary should be concise, informative, and capture the key points of the article.
                Make sure the summary is easy to read and understand.
                
                Article content:
                $content
            """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            val summary = response.text?.trim() ?: "Unable to generate summary"
            Result.success(summary)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
