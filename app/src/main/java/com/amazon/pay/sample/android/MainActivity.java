package com.amazon.pay.sample.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * アプリケーション起動時に表示される、MainActivity.
 * 実行するサンプルを下記のどちらかから選択する.
 * <ul>
 * <li>NATIVE(通常のAndroid Applicationのサンプル)</li>
 * <li>WEBVIEW(WebViewを使った、HTML&CSS&JavaScriptベースのアプリのサンプル)</li>
 * </ul>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Holder.mode = "app";
        RadioGroup rg = findViewById(R.id.mode);
        rg.check(R.id.app);
        rg.setOnCheckedChangeListener((g, id) -> Holder.mode = id == R.id.app ? "app" : "chrome");

        Button nativeButton = findViewById(R.id.button_native);
        nativeButton.setOnClickListener(
                v -> {
                    nativeButton.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), NativeActivity.class));
                });
        Button webViewButton = findViewById(R.id.button_web_view);
        webViewButton.setOnClickListener(
                v -> {
                    webViewButton.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
                });
    }

    /**
     * 「戻る」により戻ってきた時に起動するCallback.
     * 連打抑止用に無効化されたボタンを、再び有効にする.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Button nativeButton = findViewById(R.id.button_native);
        nativeButton.setEnabled(true);
        Button webViewButton = findViewById(R.id.button_web_view);
        webViewButton.setEnabled(true);
    }
}
