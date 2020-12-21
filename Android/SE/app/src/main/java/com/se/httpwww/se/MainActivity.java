package com.se.httpwww.se;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (new Handler()).postDelayed(this::change, 5000);

    }

    public void change()
    {

        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        String a = sharedPref.getString("username", null);
        String b = sharedPref.getString("password", null);
        String type="login";

        if(a!=null && b!=null) {
            if (internet_connection()) {
                BackgroundWorker backgroundworker = new BackgroundWorker(this);
                backgroundworker.execute(type, a, b);

            } else {
                final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "No internet connection.",
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                        R.color.yellow));
                snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //recheck internet connection and call DownloadJson if there is internet
                    }
                }).show();
            }
        }
        else {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {

    }




    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}

