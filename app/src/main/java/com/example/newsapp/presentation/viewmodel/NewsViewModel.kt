package com.example.newsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.model.NewsResponse
import com.example.newsapp.presentation.intent.NewsIntent
import com.example.newsapp.presentation.ui.NewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<NewsViewState>(NewsViewState.Loading)
    val viewState: StateFlow<NewsViewState> = _viewState.asStateFlow()

    private val TAG = this.javaClass.simpleName

    // Handle intents (this is where we trigger actions based on UI events)
    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.FetchNews -> getNewsData()
        }
    }

    private fun getNewsData() {
        viewModelScope.launch {
            try {
                val response: Response<NewsResponse> = getNewsUseCase.execute()

                // Handle the response and update the UI state accordingly
                if (response.isSuccessful && response.body()?.articles?.isNotEmpty() == true) {
                    val newsArticles = response.body()!!.articles
                    _viewState.value = NewsViewState.Success(newsArticles)
                    Log.d(TAG, "Fetched ${newsArticles.size} articles")
                } else {
                    _viewState.value = NewsViewState.EmptyList("No articles available.")
                    Log.e(TAG, "Error: No articles found")
                }
            } catch (e: Exception) {
                _viewState.value = NewsViewState.Error("Error fetching news.")
                Log.e(TAG, "Error fetching news: ${e.message}", e)
            }
        }
    }
}