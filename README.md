# Amazon Pay モバイル サンプルアプリ Android版
下記のAndroidアプリの実装です。
https://github.com/tauty/amazonpay-mobile-sample_server

## 動作環境
Android 7以降: Google Chrome 64以降  
[参考] https://pay.amazon.com/jp/help/202030010

## Native版
通常のAndroidアプリ向けの実装サンプルです。  

### アプリ側に購入ボタンがあるフロー
アプリ側で商品の購入数を選んで受注情報を作成し、Chrome Custom Tabsを起動してAmazon Payへのログインしてデフォルトの住所＆支払い方法を取得し、アプリに戻って確認画面を表示して購入を実施します。  

![a_app_native](img/a_app_native.gif)

住所・支払い方法を変更したい場合には確認外面で「送付先・支払い変更」ボタンをクリックして再度ブラウザを立ち上げて選択します。

![a_app_native_addr](img/a_app_native_addr.gif)  

詳細は、[flow-android.xlsx](./flow-android.xlsx)の「Native - app決済」タブ参照。  
※ 同flowには各処理のURL, 処理するClass名、HTMLテンプレート名なども記載されているので、サンプルコードを読む時にもご参照ください。

### 起動したブラウザ側に購入ボタンがあるフロー
アプリ側で商品の購入数を選んで受注情報を作成し、Chrome Custom Tabsを起動してAmazon Payへのログイン・住所＆支払い方法の選択・購入を実施し、またアプリ側に戻って購入完了画面を表示します。  
![a_chrome_native](img/a_chrome_native.gif)  

詳細は、[flow-android.xlsx](./flow-android.xlsx)の「Native - chrome決済」タブ参照。  
※ 同flowには各処理のURL, 処理するClass名、HTMLテンプレート名なども記載されているので、サンプルコードを読む時にもご参照ください。

## WebView版
WebView(アプリ内ブラウザ)を使ったアプリ向けの実装サンプルです。 

### アプリ側に購入ボタンがあるフロー
基本的な流れはNative版と同じで、WebView内で商品の購入数を選んで受注情報を作成し、Chrome Custom Tabsを起動してAmazon Payへのログインしてデフォルトの住所＆支払い方法を取得し、アプリに戻って確認画面を表示して購入を実施します。  
※ Amazon Payではセキュリティ確保のとため、URLを隠したり偽装したりできてしまうWebView上でのログイン処理を原則禁止しております。そのため、本サンプルのようにChrome Custom Tabsへ処理を飛ばす必要があります。

![a_app_webview](img/a_app_webview.gif)

住所・支払い方法を変更したい場合には確認外面で「送付先・支払い変更」ボタンをクリックして再度ブラウザを立ち上げて選択します。

詳細は、[flow-android.xlsx](./flow-android.xlsx)の「WebView - app決済」タブ参照。  
※ 同flowには各処理のURL, 処理するClass名、HTMLテンプレート名なども記載されているので、サンプルコードを読む時にもご参照ください。

### 起動したブラウザ側に購入ボタンがあるフロー
こちらもNative版と同じで、アプリ側で商品の購入数を選んで受注情報を作成し、Chrome Custom Tabsを起動してAmazon Payへのログイン・住所＆支払い方法の選択・購入を実施し、またアプリ側に戻って購入完了画面を表示します。

![a_chrome_webview](img/a_chrome_webview.gif)

詳細は、[flow-android.xlsx](./flow-android.xlsx)の「WebView - chrome決済」タブ参照。  
※ 同flowには各処理のURL, 処理するClass名、HTMLテンプレート名なども記載されているので、サンプルコードを読む時にもご参照ください。

# Android版サンプルアプリのインストール

## プロジェクトのclone
まずは、clientのandroid側にあたる本プロジェクトをcloneしてください。  
```
git clone https://github.com/tauty/amazonpay-mobile-sample_android.git
```

## プロジェクトのOpenとサンプルアプリの起動
本プロジェクトは、[Android Studio(無料)](https://developer.android.com/studio/)で開きます。そのほかのIDEでも開くことはできますが、ここでは上記のIDEでの開き方を説明します。  
まずはAndroid Studioを立ち上げます。  
*※ 下記の画面になっていない場合は、Android Studioで開いているプロジェクトを全て閉じてください。*  
![androidstudio-welcome](img/android_welcome.png)
「Import Project」 → cloneしたプロジェクトを選択 → 「Open」  
プロジェクトが開いてGradleのbuildが始まりますので、終わるまで数分お待ちください。  
終了したら、Menuの「Run」→「Run app」か、画面上部の「Run app」ボタンより、applicationを起動してください。
![androidstudio-project](img/android_project.png)
下記のようなapplicationを実行するAndroidデバイス or Virtual Device(Emulatorで起動される、仮想的なAndroidデバイス)を選択する画面が開きます。今回はEmulatorでの起動方法を説明します。  
「Create New Virtual Device」をクリックします。  
![androidstudio-select-emu](img/android_select_emu.png)
今回のサンプルはAPI Level 24 から 28で動作しますので、該当するVersionのVirtual Deviceがあればそちらを選択します。
そうでなければ、ここで「Create New Virtual Device」をクリックして、Virtual Deviceを作成します。  
![androidstudio-select-hard](img/android_select_hard.png)
左側の「Category」で「Phone」を選択し、開発に用いたい端末を選択します。  
*※特にこだわりがなければ、デフォルトで選択されているもので構いません。*  
「Next」をクリックします。
![androidstudio-select-version](img/android_select_ver.png)
API Level 24 から 28のうち好きなものをを選んで、「Next」。  
*※まだDownloadされていない場合には、「Download」より、画面の指示に従ってDownloadしてください。*
![androidstudio-select-finish](img/android_select_fin.png)
「Finish」でVirtual Deviceの生成が開始されますので、数分お待ちください。  
生成が完了すると、生成されたVirtual Deviceが選択できるようになるので、こちらを選択して「OK」。
![androidstudio-select-emu](img/android_select_emu.png)
Emulatorが立ち上がり、サンプルアプリが起動します。(1〜2分かかります。)  
<img src="img/emu_start.png" width="300">

## 自己証明書のインストール
今回のサンプルでは、server側のSSL証明書に自己証明書が使用されているため、サンプルアプリを正しく動作させるためにはその自己証明書をAndroid側にInstallする必要があります。  
ここでは、Emulatorで起動したVirtual DeviceへのInstall方法を説明します。

1. PIN lockの設定  
Androidではセキュリティのため、PINを設定しないとSSL証明書をInstallできません。  
設定画面を開き、セキュリティの設定より「画面のロック(Screen lock)」よりPINを設定してください。  
*※設定画面の開き方や各種設定は、端末やOSのバージョンによっても変わりますので、もし分からなければGoogleなどで検索してお調べください。  
参考までに、代表的な設定画面の開き方としては、アプリ一覧アイコンをクリックして選択する、ホーム画面で下からスワイプしてアプリ一覧を出して選択する、などがあります。*  
<img src="img/emu_pin.png" width="300">  

2. SSL自己証明書のDownload & Install  
Chromeを立ち上げ、下記のURLにアクセスします。  
https://10.0.2.2:8443/crt/sample.der.crt  
下記のように警告が出るので、「ADVANCED」→「PROCEED TO 10.0.2.2(UNSAFE)」  
<img src="img/emu_warn.png" width="300">  
「CONTINUE」  
<img src="img/emu_accept-download.png" width="300">  
「ALLOW」  
<img src="img/emu_allow-chrome.png" width="300">  
「DOWNLOAD」  
<img src="img/emu_download-crt.png" width="300">  
PINを聞かれるので、先ほど設定した値を入力します。  
表示された証明書Install画面にて、名前の欄に適当な名前を入力し、「VPN and apps」が選択されていることを確認して、「OK」をクリックすればインストール完了です。  
<img src="img/emu_install.png" width="300">  

あとはEmulator上でサンプルアプリを立ち上げて動作をご確認ください。

# Login with Amazonと会員連携について
今回のサンプルで提示した方式を応用することで、Amazon Accountを用いた会員連携も実現できます。
実装イメージは、[flow-android-login.xlsx](./flow-android-login.xlsx)をご参照ください。  
※ 参考 - Login with Amazonの詳細: https://developer.amazon.com/ja/docs/login-with-amazon/web-docs.html
