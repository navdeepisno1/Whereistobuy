package com.suvidha.whereistobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String version_current,version_latest;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //To fix the orientation of app
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //showUpdateDialog();
        //Handler to start new activity after 1000ms
        startSplash();
        //doProceed();
    }

    //To Get the Latest version of app from playstore.
    private class GetLatestVersion extends AsyncTask<String ,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            try {
                version_latest = Jsoup
                        .connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                        .timeout(10000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>" +
                                "span:nth-child(2) > div:nth-child(1)" +
                                "> span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return version_latest;
        }

        @Override
        protected void onPostExecute(String s) {
            version_current = BuildConfig.VERSION_NAME;
            if(version_latest!=null)
            {
                Log.e("Version",version_latest);
                if(!version_latest.equals(version_current))
                {
                    showUpdateDialog();
                }
                else
                {
                    startSplash();
                }
            }
            super.onPostExecute(s);
        }
    }

    //If App is not updated diplay update dialog
    private void showUpdateDialog()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Button update,exit;
        update = dialog.findViewById(R.id.dialog_update_button_update);
        exit = dialog.findViewById(R.id.dialog_update_button_exit);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dialog.show();
    }

    //If there is no internet display no internet dialog.
    private void showNoInternetDialog()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_noconnection);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Button proceed,exit;
        proceed = dialog.findViewById(R.id.dialog_conn_button_proceed);
        exit = dialog.findViewById(R.id.dialog_conn_button_exit);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doProceed();
                dialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dialog.show();
    }
    private void startSplash()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //To start new activity
                startActivity(new Intent(MainActivity.this,Home.class));
                //To finish current activity
                finish();
            }
        },1000);
    }

    private boolean checkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    private void doProceed()
    {
        if(checkConnection()) {
            new GetLatestVersion().execute();
        }
        else
        {
            showNoInternetDialog();
        }
    }
}