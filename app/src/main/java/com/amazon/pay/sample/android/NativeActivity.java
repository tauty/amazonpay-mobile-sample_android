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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.*;

/**
 * MainActivityから起動される、NATIVEサンプル用Activity(通常のAndroid Applicationのサンプル).
 * Kindle Fire HD8とKindle Fire HD10の購入個数を指定して「購入手続きへ」ボタンをクリックすると受注が作成されて、
 * AmazonPayボタンでChrome Custom Tabsの購入フローが起動する.
 */
public class NativeActivity extends AppCompatActivity {

    private volatile String token;

    private TextView errorMsg = null;
    private EditText hd8Num = null;
    private EditText hd10Num = null;
    private Button registerButton = null;
    private ImageButton amznButton = null;

    private static final Pattern NUM_PTN = Pattern.compile("(0|[1-9][0-9]*)");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        errorMsg = findViewById(R.id.error_msg);
        hd8Num = findViewById(R.id.hd8_num);
        hd10Num = findViewById(R.id.hd10_num);
        registerButton = findViewById(R.id.register_button);
        amznButton = findViewById(R.id.amazon_pay_button);

        // 初期状態では非表示
        amznButton.setVisibility(INVISIBLE);

        registerButton.setOnClickListener(v -> {
            if(isNotNumber(hd8Num) || isNotNumber(hd10Num)) {
                errorMsg.setText("不正な数値です！");
                return;
            }
            if(isZero(hd8Num) && isZero(hd10Num)) {
                errorMsg.setText("せめて一つは買ってください！");
                return;
            }

            errorMsg.setText("");
            hd8Num.setEnabled(false);
            hd10Num.setEnabled(false);
            registerButton.setVisibility(INVISIBLE);
            amznButton.setVisibility(VISIBLE);

            registerOrder();
        });

        amznButton.setOnClickListener(v -> {
            if (NativeActivity.this.token == null) return;
            amznButton.setEnabled(false);

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

            tabsIntent.launchUrl(getApplicationContext(), Uri.parse(getString(R.string.base_url) + "button?token=" + this.token));
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
        final Request request = new Request.Builder()
                .url(getString(R.string.base_url) + "android/create_order_rest")
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
            }
        });
    }

    private FormBody createFormBody() {
        Builder builder = new Builder();
        builder.add("hd8", String.valueOf(hd8Num.getText()));
        builder.add("hd10", String.valueOf(hd10Num.getText()));
        return builder.build();
    }

    private boolean isNotNumber(EditText et) {
        return !NUM_PTN.matcher(String.valueOf(et.getText())).matches();
    }

    private boolean isZero(EditText et) {
        return Objects.equals(String.valueOf(et.getText()), "0");
    }
}
