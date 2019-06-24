package com.example.alphamobilecolombia.mvp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.alphamobilecolombia.R;

public class WebViewUpdFilesActivity extends Activity {
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_updfiles);

        webView = (WebView) findViewById(R.id.webView_updfiles);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.com");
    }
}