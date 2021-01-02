package com.suvidha.whereistobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Home extends AppCompatActivity {

    //Variables Declaration
    Context context = this;
    WebView webView_main;
    String sURL = "https://www.whereistobuy.com/";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Variable Definition
        webView_main = findViewById(R.id.home_webview_main);

        //Webviewclient to open all links in app itself
        webView_main.setWebViewClient(new Browser(context));

        //All webview setting to decrease the load time of app
        webView_main.getSettings().setJavaScriptEnabled(true);
        webView_main.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView_main.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView_main.getSettings().setLoadsImagesAutomatically(true);
        webView_main.getSettings().setAppCacheEnabled(true);
        webView_main.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView_main.getSettings().setDomStorageEnabled(true);
        webView_main.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView_main.getSettings().setUseWideViewPort(true);
        webView_main.getSettings().setSaveFormData(true);
        webView_main.getSettings().setSavePassword(true);
        webView_main.getSettings().setEnableSmoothTransition(true);
        webView_main.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView_main.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView_main.setWebChromeClient(new WebChromeClient());
        webView_main.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView_main.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView_main, true);
        webView_main.loadUrl(sURL);
    }

    @Override
    public void onBackPressed() {
        if(webView_main.canGoBack())
        {
            webView_main.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}