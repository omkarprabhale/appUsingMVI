package com.example.newsapp.data.repository

import com.example.newsapp.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"

/*
Each user should have a unique API key. To create one, log in at https://newsapi.org/ and add.
*/
const val API_KEY = ""

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Response<NewsResponse>

}
