package com.example.newsapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.window.OnBackInvokedDispatcher
import com.example.newsapp.R

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        val webView =  findViewById<WebView>(R.id.webView);
        val url: String? = intent.getStringExtra("url")
        webView.webChromeClient = CustomWebChromeClient()
        url?.let { webView.loadUrl(it) }
    }

    class CustomWebChromeClient : WebChromeClient() {
        override fun onCloseWindow(window: WebView?) {}
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return true
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }
}