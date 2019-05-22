package com.amazon.pay.sample.android;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AmazonPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_pay);

        String token = this.getIntent().getStringExtra("token");

        CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        tabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://10.0.2.2:8443/button?token=" + token));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.finish();
    }
}
