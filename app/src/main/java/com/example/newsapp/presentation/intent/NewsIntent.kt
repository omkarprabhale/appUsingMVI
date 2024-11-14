package com.example.newsapp.presentation.intent

sealed class NewsIntent {
    object FetchNews : NewsIntent()
}
