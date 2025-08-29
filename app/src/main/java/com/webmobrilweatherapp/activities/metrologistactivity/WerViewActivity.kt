package com.webmobrilweatherapp.activities.metrologistactivity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.R


class WerViewActivity : AppCompatActivity() {


    var type=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wer_view)
        // Find the WebView by its unique ID

        // Find the WebView by its unique ID
        val webView = findViewById<WebView>(R.id.webview)
        val imgbackicon = findViewById<ImageView>(R.id.imgbackicon)
        imgbackicon.setOnClickListener {
            onBackPressed()
        }

        type = intent.getStringExtra("webviewurl").toString()
        if (type=="1"){
            webView.loadUrl("https://weatherdemocracy.com/term_conditions_application")

        }else if (type=="2"){
            webView.loadUrl("https://weatherdemocracy.com/privacy_policy_application")

        }else{
            webView.loadUrl("https://weatherdemocracy.com/about_us")
        }

        // loading https://www.geeksforgeeks.org url in the WebView.

        // loading https://www.geeksforgeeks.org url in the WebView.
       // webView.loadUrl("https://weatherdemocracy.com/term_conditions_application")

        // this will enable the javascript.

        // this will enable the javascript.
        webView.settings.javaScriptEnabled = true

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClient()
    }
}