package com.se.httpwww.se;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Dell on 4/25/2017.
 */


public class Register extends AppCompatActivity {


    EditText etfull,etname,etpass,etmob,etrepass,etmail;
    String fullname,username,pass,repass,contact,email;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etname=(EditText)findViewById(R.id.name);
        etpass=(EditText)findViewById(R.id.pass);
        etrepass=(EditText)findViewById(R.id.repass);
        etmob=(EditText)findViewById(R.id.contact);
        etfull=(EditText)findViewById(R.id.fullname);
        etmail=(EditText)findViewById(R.id.myemail);

         go=(Button)findViewById(R.id.lin);
        go.setClickable(true);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReg();
            }
        });
    }



    public void onReg() {
        go.setClickable(false);
        fullname = etfull.getText().toString();
        username = etname.getText().toString();
        repass = etrepass.getText().toString();
        pass = etpass.getText().toString();
        contact = etmob.getText().toString();
        email=etmail.getText().toString();

        if (email.contains("@gmail.com") || email.contains("@yahoo.com") || email.contains("@live.com")
                || email.contains("@rocketmail.com") || email.contains("@outlook.com") || email.contains("@hotmail.com") || (email.contains("@") && email.contains(".edu.pk")) ) {

            if (repass.equals(pass)) {

                if ((username.length() > 3) && (username.length() < 50) && (pass.length() > 7) && (pass.length() < 30)
                        && (fullname.length() > 0) && (fullname.length() < 30) && (contact.length() == 11)) {
                    String method = "register";
                    if(internet_connection()){
                        User.getInstance().setTempSignUpUserName(username);
                        BackgroundTask backgroundTask = new BackgroundTask(this);
                        backgroundTask.execute(method, fullname, username, pass, contact,email);
                        go.setClickable(true);
                        // finish();
                    }
                    else{
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


                    if(contact.length()!=11)
                    {
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                        myAlert.setMessage("Invalid Contact").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ;
                            }

                        }).create();
                        myAlert.show();
                    }
                    else if(!(pass.length() > 7) && !(pass.length() < 30))
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
                    else if(!(email.length() > 9) && !(email.length() < 50)){
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                        myAlert.setMessage("Email Not Acceptable").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ;
                            }

                        }).create();
                        myAlert.show();
                    }
                    else
                    {
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                        myAlert.setMessage("Invalid Password or Email").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
            } else {

                go.setClickable(true);
                AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setMessage("Passwords are not Same").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }

                }).create();
                myAlert.show();
            }
        }

        else
        {
            go.setClickable(true);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register.this);
            alertDialog.setTitle("Invalid");
            alertDialog.setMessage("Email not Acceptable");
            alertDialog.show();

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
