package com.suvidha.whereistobuy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Browser extends WebViewClient {
    private Context context;
    public Browser(Context context)
    {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = "SSL Certificate error.";
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                message = "The certificate authority is not trusted.";
                break;
            case SslError.SSL_EXPIRED:
                message = "The certificate has expired.";
                break;
            case SslError.SSL_IDMISMATCH:
                message = "The certificate Hostname mismatch.";
                break;
            case SslError.SSL_NOTYETVALID:
                message = "The certificate is not yet valid.";
                break;
        }
        message += " Do you want to continue anyway?";

        builder.setTitle("SSL Certificate Error");
        builder.setMessage(message);
        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.proceed();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        view.setVisibility(View.INVISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.setVisibility(View.VISIBLE);
        super.onPageFinished(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String Url = String.valueOf(request.getUrl());
        Log.e("Tag",Url);
        if(Url.startsWith("https://whereistobuy.com") || Url.startsWith("https://www.whereistobuy.com") || Url.startsWith("http://whereistobuy.com") || Url.startsWith("http://www.whereistobuy.com"))
        {
                return false;
        }
        if(Url.equals("https://play.google.com/store/apps/details?id=com.suvidha.whereistobuy") || Url.equals("https://solutiontrick.tk/how-to-register-as-a-shopkeeper/") || Url.contains("facebook") || Url.contains("instagram")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
            view.getContext().startActivity(intent);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }

}
