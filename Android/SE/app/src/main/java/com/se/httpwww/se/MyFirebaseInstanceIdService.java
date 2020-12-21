package com.se.httpwww.se;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override

    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),refreshedToken);
        editor.commit();


        Log.d("myfirebaseid","Refreshed token: "+refreshedToken);

    }

}
//cSDojghSCvw:APA91bEuCNIzxhV6wEcMIXZ8qBSQGzO1Oq37i1Mt6Ycjcq4sCSc2LyeSXGElkuhkqBfOwDpV7xtqUwQyiNhz7n25bhJ4asTCS5W2j7KLDrqPwRIW8qLjQzRz8_naudSiFHewt1SMrv1Z