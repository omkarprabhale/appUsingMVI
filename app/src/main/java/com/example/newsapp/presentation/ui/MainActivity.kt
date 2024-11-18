package com.example.newsapp.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.domain.model.Articles
import com.example.newsapp.presentation.intent.NewsIntent
import com.example.newsapp.presentation.viewmodel.NewsViewModel
import com.example.newsapp.presentation.ui.theme.NewsAppTheme
import com.example.newsapp.utils.Network
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Network.isNetworkAvailable(this)) {
                        newsViewModel.onIntent(NewsIntent.FetchNews)
                        GetNews(newsViewModel)
                    } else {
                        Nointernet()
                    }

                }
            }
        }

    }


    @Composable
    private fun Nointernet() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = stringResource(R.string.no_network),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(15.dp),
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = Color.DarkGray,

                )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    @Composable
    fun GetNews(newsViewModel: NewsViewModel) {
        val viewState by newsViewModel.viewState.collectAsState()
        when (viewState) {
            is NewsViewState.Loading -> {
                LoadingIndicator()
            }

            is NewsViewState.Success -> {
                val newsList = (viewState as NewsViewState.Success).newsList
                NewsData(newsList)
            }

            is NewsViewState.Error -> {
                ShowErrorMessage("0 news found")
            }

            is NewsViewState.EmptyList -> TODO()
        }

    }

    private @Composable
    fun NewsData(newsList: List<Articles>) {
        Column() {

            Text(
                text = "TOP Headlines",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                fontFamily = FontFamily.SansSerif,
                fontSize = 35.sp

            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                items(newsList) { it ->
                    NewsCard(data = it)
                }

            }
        }
    }

    private @Composable
    fun LoadingIndicator() {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.secondary,
            strokeWidth = 4.dp

        )
    }


    @Composable
    fun NewsCard(
        data: Articles
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    getDetailsOfNews(data)

                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {
            Column() {
                Text(
                    text = data.title.toString(),
                    modifier = Modifier.padding(all = 5.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp
                )

                Row() {
                    Text(
                        text = data.author.toString(),
                        modifier = Modifier.padding(all = 5.dp),
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Blue,
                        fontSize = 10.sp
                    )

                    Text(
                        text = data.publishedAt.toString(),
                        modifier = Modifier.padding(all = 5.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 10.sp
                    )
                }
            }
        }


    }

    @Composable
    fun ShowErrorMessage(message: String) {
        Text(
            text = message,
            color = Color.Red,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(16.dp)
        )
    }

    private fun getDetailsOfNews(data: Articles) {

        val url = data.url
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

}



