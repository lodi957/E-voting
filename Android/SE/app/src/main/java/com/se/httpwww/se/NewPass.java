package com.se.httpwww.se;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPass extends Activity {

    private Button sendCode;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);


        sendCode=(Button)findViewById(R.id.sendCode);
        email=(EditText)findViewById(R.id.codeEmail);


        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String e= email.getText().toString();
                if(e!=null && !e.contentEquals("") && !e.contentEquals(" ")) {
                    if ((e.length() > 0) && (email.length()<20)) {
                        if (internet_connection()) {
                            User.getInstance().setTempSignUpUserName(e);
                            String type = "forget";
                            ForgetBackground forgetbackground = new ForgetBackground(NewPass.this);
                            forgetbackground.execute(type, e);
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
                    } else {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewPass.this);
                        alertDialog.setTitle("Invalid");
                        alertDialog.setMessage("This CNIC not Acceptable");
                        alertDialog.show();

                    }
                }

                else
                {
                    Toast.makeText(NewPass.this,"Please Enter your CNIC",Toast.LENGTH_LONG).show();
                }
            }
        });
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
