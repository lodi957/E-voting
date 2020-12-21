package com.se.httpwww.se;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String imgUrl;// = "https://api.androidhive.info/images/minion.jpg";
    private NotificationUtils notificationUtils;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        String type = data.get("my_type");



            String myCustomUser = data.get("my_custom_user");
            String myCustomFullname = data.get("my_custom_fullname");
            String myCustomContact = data.get("my_custom_contact");
            String myCustomLoc = data.get("my_custom_location");
            String myCustomList = data.get("my_custom_grocery");
            String GetterLoc = data.get("GetterLoc");
            type = data.get("my_type");
            String body = data.get("body");
            String title = data.get("title");
            String total = data.get("total");
            String lat = data.get("latitude");
            String lng = data.get("longitude");
            String lat2 = data.get("latitudeGet");
            String lng2 = data.get("longitudeGet");
            String id = data.get("id");




            send(title, body,myCustomUser,myCustomFullname,myCustomContact ,myCustomLoc,myCustomList,GetterLoc,type,total,lat,lng,lat2,lng2,id);
    }





    public void send(String title,String message, String myCustomUser,String myCustomFullname, String myCustomContact , String myCustomLoc, String myCustomList,String GetterLoc, String type,String total,String lat,String lng,String lat2,String lng2,String id)
    {


        Intent intent = new Intent(this, MainPage.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("my_custom_user",myCustomUser);
        intent.putExtra("my_custom_contact",myCustomContact);
        intent.putExtra("my_custom_location",myCustomLoc);
        intent.putExtra("my_custom_grocery",myCustomList);
        intent.putExtra("GetterLoc",GetterLoc);
        intent.putExtra("my_type",type);
        intent.putExtra("total",total);
        intent.putExtra("longitude",lng);
        intent.putExtra("latitude",lat);
        intent.putExtra("longitudeGet",lng2);
        intent.putExtra("latitudeGet",lat2);
        intent.putExtra("id",id);
        intent.putExtra("myCustomFullname",myCustomFullname);




        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID,
                intent, 0);


//      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//      //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setContentTitle(title)
//                .setTicker("TASK ALERT")
//                .setContentText(message)
//                .setDefaults(-1)
//                .setSmallIcon(R.drawable.s)
//                .setAutoCancel(true)
//               // .setSound(soundUri)
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Random random = new Random();
//        int num = random.nextInt(99999-1000)+1000;
//        notificationManager.notify(num, notificationBuilder.build());
//       // notificationManager.cancelAll();


        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            //Intent resultIntent = new Intent(getApplicationContext(), Accept.class);
            // resultIntent.putExtra("message", message);

            if (TextUtils.isEmpty(imgUrl)) {
                showNotificationMessage(getApplicationContext(), title, message, pendingIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, pendingIntent, imgUrl);
            }

        } else {
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), MainPage.class);
            resultIntent.putExtra("message", message);

            // check for image attachment
            if (TextUtils.isEmpty(imgUrl)) {
                showNotificationMessage(getApplicationContext(), title, message, pendingIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, pendingIntent, imgUrl);
            }
        }


    }


    private void showNotificationMessage(Context context, String title, String message, PendingIntent intent) {
        notificationUtils = new NotificationUtils(context);
        notificationUtils.showNotificationMessage(title, message,intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, PendingIntent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        notificationUtils.showNotificationMessage(title, message,intent, imageUrl);
    }

}






