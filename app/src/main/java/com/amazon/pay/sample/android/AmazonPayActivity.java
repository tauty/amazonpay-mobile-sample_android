package com.amazon.pay.sample.android;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * WebView内のブラウザ内でAmazonPayボタンをクリックした時に起動されて、
 * Chrome Custom Tabsの購入フローを起動するActivity.
 */
public class AmazonPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_pay);

        String token = this.getIntent().getStringExtra("token");

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

        // 起動するBrowserにChromeを指定
        tabsIntent.intent.setPackage("com.android.chrome");

        // 別のActivityへの遷移時に、自動的にChrome Custom Tabsを終了させるためのフラグ設定.
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Chrome Custom Tabs終了時に、Historyとして残らないようにするためのフラグ設定.
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        tabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://10.0.2.2:8443/button?token=" + token));
    }

    /**
     * Chrome Custom Tabsの購入フローから「戻る」により戻ってきた時に起動するCallback.
     * ユーザにはWebViewActivityに戻ったように見せかけるため、本Activityを終了させている.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }
}
