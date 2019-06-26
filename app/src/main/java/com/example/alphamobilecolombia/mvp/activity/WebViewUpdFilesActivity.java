package com.example.alphamobilecolombia.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.alphamobilecolombia.R;

public class WebViewUpdFilesActivity extends Activity {
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_updfiles);

        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");
        String transaccion = sharedPref.getString("codigoTransaccion", "");

        getIntent().getStringExtra("EXTRA_SESSION_ID");

        String url = "http://181.57.145.20:6235/Optimus/CargueDocumentosMobile/GuardarDocumentosMobile.aspx?IdSujetoCredito=" + transaccion + "&IdUsuarioRegistro=" + user;

        webView = (WebView) findViewById(R.id.webView_updfiles);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        //webView.setWebViewClient(new WebViewClient() { @Override public void onPageFinished(WebView view, String url) { super.onPageFinished(view, url);
        // if (url.endsWith("#closeWebview")){ webView.setVisibility(View.GONE); } } });
    }
}