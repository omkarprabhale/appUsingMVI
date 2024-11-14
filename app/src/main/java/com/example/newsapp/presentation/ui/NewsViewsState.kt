package com.example.newsapp.presentation.ui

import com.example.newsapp.data.model.Articles

sealed class NewsViewState {
    object Loading : NewsViewState()
    data class Success(val newsList: List<Articles>) : NewsViewState()
    data class Error(val error: String) : NewsViewState()
}