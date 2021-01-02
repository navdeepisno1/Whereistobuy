package com.suvidha.whereistobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DeepLink extends AppCompatActivity {
    Context context=this;
    WebView webView_main;
    String sURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        //String action = intent.getAction();
        Uri data = intent.getData();
        //Variable Definition
        webView_main = findViewById(R.id.home_webview_main);
        //Webviewclient to open all links in app itself
        webView_main.setWebViewClient(new BrowserV2(context));
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
        webView_main.loadUrl(String.valueOf(data));
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