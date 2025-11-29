package com.example.strayanimals.ui.encyclopedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.strayanimals.R
import com.example.strayanimals.databinding.ActivityWebViewEncBinding
import android.content.Intent




class WebViewEncActivity : AppCompatActivity() {
private lateinit var mUrl:String

private lateinit var mBinding: ActivityWebViewEncBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWebViewEncBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mUrl = intent.getStringExtra("url").toString()
        // Inflate the layout for this fragment
//        mUrl = "https://baijiahao.baidu.com/s?id=1610031367408708797&wfr=spider&for=pc"

        val webView: WebView = mBinding.encycWebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        Log.e("frag", webView.id.toString())
        webView.loadUrl(mUrl)
    }
}