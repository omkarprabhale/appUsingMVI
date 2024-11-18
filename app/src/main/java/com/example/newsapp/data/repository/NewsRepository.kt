package com.example.newsapp.data.repository

import com.example.newsapp.domain.model.NewsResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsInterface: NewsInterface
) {
    suspend fun fetchNews(): Response<NewsResponse> {
        val response = newsInterface.getHeadlines("us", 1)
        if (response.isSuccessful) {
            return response
        } else {
            throw Exception("Error fetching news")
        }
    }
}