package com.se.httpwww.se;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Settings extends AppCompatActivity{

    TextView u,m,v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        v=(TextView)findViewById(R.id.fullnameSettings);
        u=(TextView)findViewById(R.id.usernameSettings);
        m=(TextView)findViewById(R.id.mobileSettings);


        String str="{fa-user #062136} Name: ";
        str=str.concat(User.getInstance().getFullname());
        u.setText(str);



        String str3="{fa-user #062136} CNIC: ";
        str3=str3.concat(User.getInstance().getName());
        v.setText(str3);

      //  String str2="{fa-mobile #062136} Mobile: ";
     //   str2=str2.concat(Info.getInstance().getContact());
     //   m.setText(str2);
        m.setVisibility(View.INVISIBLE);


    }


    public void Logout(View view)
    {

        User.getInstance().myClear();

        SharedPreferences sharedPref=getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();

        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);

    }


    public void changePass(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Pass.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, MainPage.class);
        startActivity(i);
    }



}