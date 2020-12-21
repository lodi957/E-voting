package com.se.httpwww.se;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Dell on 1/11/2018.
 */

public class CodeEntry extends Activity {

    TextView tv;
    Button verifyCode;
    EditText codeentry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeentry);

        tv=(TextView)findViewById(R.id.tvforget);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode();
            }
        });

        codeentry=(EditText)findViewById(R.id.codeentry1);

        verifyCode=(Button)findViewById(R.id.verifyCode);

        verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = codeentry.getText().toString();


                if (e != null && !e.contentEquals("") && !e.contentEquals(" ")) {

                        String type = "checkCode";
                        if (internet_connection()) {
                            CheckCodeBackground checkCodeBackground = new CheckCodeBackground(CodeEntry.this);
                            checkCodeBackground.execute(type, e);
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
            }
        });

    }



    public void resendCode()
    {

        Intent intent=new Intent(CodeEntry.this,NewPass.class);
        startActivity(intent);
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
