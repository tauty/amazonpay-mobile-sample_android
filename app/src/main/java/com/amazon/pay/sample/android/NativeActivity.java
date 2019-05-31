package com.amazon.pay.sample.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * MainActivityから起動される、NATIVEサンプル用Activity(通常のAndroid Applicationのサンプル).
 * Kindle Fire HD8とKindle Fire HD10の購入個数を指定すると自動で受注が作成されて、
 * AmazonPayボタンでChrome Custom Tabsの購入フローが起動する.
 */
public class NativeActivity extends AppCompatActivity {

    private volatile boolean isOkToPay = false;
    private volatile String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        MyTextWatcher watcher = new MyTextWatcher();
        EditText hd8Num = findViewById(R.id.hd8_num);
        hd8Num.addTextChangedListener(watcher);
        EditText hd10Num = findViewById(R.id.hd10_num);
        hd10Num.addTextChangedListener(watcher);

        registerOrder();

        ImageButton button = findViewById(R.id.amazon_pay_button);
        button.setOnClickListener(v -> {
            if (!NativeActivity.this.isOkToPay) return;
            button.setEnabled(false);

            CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder().build();

            // 起動するBrowserにChromeを指定
            tabsIntent.intent.setPackage("com.android.chrome");

            // 別のActivityへの遷移時に、自動的にChrome Custom Tabsを終了させるためのフラグ設定.
            tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Chrome Custom Tabs終了時に、Historyとして残らないようにするためのフラグ設定.
            tabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            tabsIntent.launchUrl(getApplicationContext(), Uri.parse("https://10.0.2.2:8443/button?token=" + this.token));
        });
    }

    /**
     * 「戻る」により戻ってきた時に起動するCallback.
     * 連打抑止用に無効化されたボタンを、再び有効にする.
     */
    @Override
    protected void onResume() {
        super.onResume();
        ImageButton button = findViewById(R.id.amazon_pay_button);
        button.setEnabled(true);
    }

    private void registerOrder() {
        this.isOkToPay = false;
        final Request request = new Request.Builder()
                .url("https://10.0.2.2:8443/registerOrder")
                .header("User-Agent", "Example client")
                .post(createFormBody())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("[native]", "The error occurs during the registering the order.", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NativeActivity.this.token = response.body().string();
                NativeActivity.this.isOkToPay = true;
            }
        });
    }

    private FormBody createFormBody() {
        Builder builder = new Builder();
        if (this.token != null) {
            builder.add("token", this.token);
        }
        applyTextView(builder, R.id.hd8_num, "hd8");
        applyTextView(builder, R.id.hd10_num, "hd10");
        return builder.build();
    }

    private void applyTextView(Builder builder, int id, String key) {
        EditText et = findViewById(id);
        String s = String.valueOf(et.getText());
        System.out.println("applyTextView: " + s);
        builder.add(key, s.isEmpty() ? "0" : s);
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            registerOrder();
        }
    }

}
