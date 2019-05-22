package com.amazon.pay.sample.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nativeButton = findViewById(R.id.button_native);
        nativeButton.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), NativeActivity.class)));
        Button webViewButton = findViewById(R.id.button_web_view);
        webViewButton.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), WebViewActivity.class)));
    }
}
