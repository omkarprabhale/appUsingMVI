package com.example.newsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.presentation.intent.NewsIntent
import com.example.newsapp.presentation.ui.NewsViewState
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var news: MutableStateFlow<List<Articles>> = MutableStateFlow(listOf())
    val TAG = this.javaClass.toString()
    private val _viewState = MutableStateFlow<NewsViewState>(NewsViewState.Loading)
    val viewState: StateFlow<NewsViewState> = _viewState.asStateFlow()
    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.FetchNews -> getNewsData()
        }
    }

    fun getNewsData() {
        viewModelScope.launch {

            try {
                val newsResponse: Response<NewsResponse> = newsRepository.fetchNews()
                if (newsResponse.body()?.articles?.isNotEmpty() == true) {
                    Log.d(TAG, "here are the news")
                    news.value = newsResponse.body()!!.articles
                    _viewState.value = NewsViewState.Success(news.value)
                } else {
                    _viewState.value = NewsViewState.Error("Error fetching news")
                }
            } catch (e: Exception) {
                _viewState.value = NewsViewState.Error("Error fetching news")
                Log.e(TAG, "Error fetching news: ${e.message}", e)
            }

        }
    }

}