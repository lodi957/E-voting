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
import android.widget.EditText;
import android.widget.TextView;

public class Pass extends AppCompatActivity{

    EditText curr, newpass, renewpass;
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);

        curr=(EditText)findViewById(R.id.currPass);
        newpass=(EditText)findViewById(R.id.newPass);
        renewpass=(EditText)findViewById(R.id.reNewPass);

    }


    public void SubmitPass(View view)
    {
        String currS=curr.getText().toString();
        String newp=newpass.getText().toString();
        String renewp=renewpass.getText().toString();

        if(newp.equals(renewp))
        {
            if(currS.equals(User.getInstance().getPass()))
            {
                if((newp.length() > 7) && (newp.length() < 30)) {
                    if (internet_connection()) {
                        String method = "changePass";

                        ChangePass changePass = new ChangePass(this);
                        changePass.execute(method, newp, renewp, currS);
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
                else
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
            }
            else
            {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
                myAlert.setMessage("Incorrect Current Password").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }

                }).create();
                myAlert.show();
            }
        }
        else{
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



    @Override
    public void onBackPressed() {

        super.onBackPressed();
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
