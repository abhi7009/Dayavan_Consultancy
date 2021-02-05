package com.abhishek.dayavanconsultancy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "Wifi Connected", Toast.LENGTH_SHORT).show();
                webView.loadUrl("http://bmango.in/vehicles/");
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "Mobile Data Connected", Toast.LENGTH_SHORT).show();
                webView.loadUrl("http://bmango.in/vehicles/");

            }

        } else {
            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            webView.loadUrl("file:///android_asset/Bmango.html");

        }

    }

    public static class NetworkChangeReceiver extends BroadcastReceiver {

        MediaBrowser.ConnectionCallback connectionChangeCallback;

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();


            if (connectionChangeCallback != null) {
                connectionChangeCallback.onConnectionChange(isConnected);
            }

        }
        public void setConnectionChangeCallback(ConnectionChangeCallback
                                                        connectionChangeCallback) {
            this.connectionChangeCallback = (MediaBrowser.ConnectionCallback) connectionChangeCallback;
        }


        public interface ConnectionChangeCallback {

            void onConnectionChange(boolean isConnected);

        }

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}