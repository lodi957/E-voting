package com.se.httpwww.se;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.se.httpwww.se.R;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {


    EditText user,pass;
    Button go;
    TextView forget,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password1);
        forget=(TextView) findViewById(R.id.tvforget);
        signup=(TextView) findViewById(R.id.tv2);

        go=(Button)findViewById(R.id.btlogin);
        go.setClickable(true);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    go.setClickable(false);
                    onLogin();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aesi();
            }




        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userReg();
            }
        });


    }


    public void userReg()
    {
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);
    }


    public void aesi()
    {

        Intent i = new Intent(getApplicationContext(), NewPass.class);
        startActivity(i);
    }


    public void onLogin() throws ExecutionException, InterruptedException {

        go.setClickable(false);

        String username= "";
        username= user.getText().toString();
        String password= "";
        password= pass.getText().toString();
        String type="login";

            if(password.length()>=8) {
                    if (internet_connection()) {
                        BackgroundWorker backgroundworker = new BackgroundWorker(this);
                        backgroundworker.execute(type, username, password);

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




            }else
            {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setMessage("Please Enter Atleast 8 Characters Password").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }

                }).create();
                myAlert.show();
            }



        go.setClickable(true);
    }




    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
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
