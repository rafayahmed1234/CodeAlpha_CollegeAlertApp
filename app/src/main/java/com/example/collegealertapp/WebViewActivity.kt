package com.example.collegealertapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity



class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webView)

        // Get URL and title from intent
        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        // Set title
        supportActionBar?.title = title

        // Configure WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Load URL
        url?.let { webView.loadUrl(it) }
    }

    val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
            // Check if the WebView can go back
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                // If the WebView cannot go back, disable this callback
                // and call the default back press behavior (which may be to close the activity)
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}


