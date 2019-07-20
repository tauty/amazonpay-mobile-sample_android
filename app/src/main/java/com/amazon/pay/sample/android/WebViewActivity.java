package com.amazon.pay.sample.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * MainActivityから起動される、WEBVIEWサンプル用Activity(WebViewを使ったAndroid Applicationのサンプル).
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(getString(R.string.base_url) + "android/order");
    }
}
