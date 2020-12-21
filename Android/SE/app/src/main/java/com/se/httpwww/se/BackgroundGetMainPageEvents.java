package com.se.httpwww.se;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dell on 7/30/2017.
 */

public class BackgroundGetMainPageEvents extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    InputStream is=null;
    String line=null;
    String result=null;

    BackgroundGetMainPageEvents(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {

        String address ="http://";
        address=address.concat(User.getInstance().getIp());
        address=address.concat("/SE/GetVotingEventsForMain.php");

        if(internet_connection()) {
            try {


                String user_name = User.getInstance().getName();

                URL url = new URL(address);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");


                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                is = new BufferedInputStream(con.getInputStream());


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            //PARSE JSON DATA

            try {

                if(internet_connection() && result!=null) {

                //    JSONObject jo = new JSONObject(result);
                 //   JSONArray ja = jo.getJSONArray("result");

                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;

                    String id,name,desc,ins,pos;

                    User.getInstance().votingEventsList.clear();

                    for(int i=0;i<ja.length();i++)
                    {
                        jo=ja.getJSONObject(i);

                        id=jo.getString("id");
                        name=jo.getString("name");
                       // eid=jo.getString("eid");
                       // eid="1";
                        desc=jo.getString("description");
                        ins=jo.getString("institute");
                        pos=jo.getString("poster");



                        User.getInstance().votingEventsList.add(new VotingEventClass(id,name,desc,ins,pos));

                    }

                    result="Success";
                    return result;

                }
                else
                {
                    String result="Connection error";
                    return result;
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        else
        {
            String result="Connection error";
            return result;
        }
        return null;
    }


    protected void onPreExecute() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }


    @Override
    protected void onPostExecute(String result) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();}



        }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}