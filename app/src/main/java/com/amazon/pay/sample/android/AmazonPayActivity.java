package com.amazon.pay.sample.android;

import android.content.Context;
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
        String appKey = this.getIntent().getStringExtra("appKey");

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

        // 起動するBrowserにChromeを指定
        // Note: Amazon Payでは他のブラウザがサポート対象に入っていないため、ここではChromeを指定している.
        // [参考] https://pay.amazon.com/jp/help/202030010
        // もしその他のChrome Custom Tabs対応のブラウザを起動する必要がある場合には、下記リンク先ソースなどを参考に実装する.
        // [参考] https://github.com/GoogleChrome/custom-tabs-client/blob/master/shared/src/main/java/org/chromium/customtabsclient/shared/CustomTabsHelper.java#L64
        tabsIntent.intent.setPackage("com.android.chrome");

        // 別のActivityへの遷移時に、自動的にChrome Custom Tabsを終了させるためのフラグ設定.
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Chrome Custom Tabs終了時に、Historyとして残らないようにするためのフラグ設定.
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        // token, appKeyの保持
        Holder.appToken = token;
        Holder.appKey = appKey;

        String path = "button?token=" + token + "&mode=" + Holder.mode;
        String showWidgets = this.getIntent().getStringExtra("showWidgets");
        if(showWidgets != null) {
            path += "&showWidgets=true";
        }

        // Chrome Custom Tabsの起動
        tabsIntent.launchUrl(getApplicationContext(), Uri.parse(getString(R.string.base_url) + path));
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
