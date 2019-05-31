package com.amazon.pay.sample.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 購入完了後に表示されるActivity.
 * 受注の情報と取得した購入者情報などを表示する.
 */
public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanks);

        Button topButton = findViewById(R.id.button);
        topButton.setOnClickListener(
                v -> {
                    topButton.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                });

        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        String token = this.getIntent().getStringExtra("token");
        String data = "token=" + token;
        webView.postUrl("https://10.0.2.2:8443/thanks", data.getBytes());
    }

    /**
     * 「戻る」により戻ってきた時に起動するCallback.
     * 連打抑止用に無効化されたボタンを、再び有効にする.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Button topButton = findViewById(R.id.button);
        topButton.setEnabled(true);
    }
}
