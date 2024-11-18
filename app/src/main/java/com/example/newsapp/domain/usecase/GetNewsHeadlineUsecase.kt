package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.model.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(): Response<NewsResponse> {
        val response = newsRepository.fetchNews()
        return response
    }
}