package com.se.httpwww.se;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class ForgetBackground extends AsyncTask<String,Void,String>{
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    ForgetBackground(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);

    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url ="http://";
        reg_url=reg_url.concat(User.getInstance().getIp());
        reg_url=reg_url.concat("/SE/ForgetPassword.php");



        String method = params[0];

        if (method.equals("forget")) {
            try {

                String code = params[1];

              //  String user_name=Info.getInstance().getName();
             //   String password=Info.getInstance().getPass();

                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post_data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");//+"&"+
                    //    URLEncoder.encode("user_name" ,"UTF-8")+"="+URLEncoder.encode(user_name ,"UTF-8")+"&"+URLEncoder.encode("password" ,"UTF-8")+"="+URLEncoder.encode(password ,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String result) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();}



        if(result.contains("No Such CNIC Registered"))
        {
            AlertDialog.Builder myAlert=new AlertDialog.Builder(context);
            myAlert.setMessage(result).setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }).create();
            myAlert.show();
        }
        else {

            AlertDialog.Builder myAlert=new AlertDialog.Builder(context);
            myAlert.setMessage("Email Sent").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){

                    Intent intent = new Intent(context, CodeEntry.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }).create();
            myAlert.show();

            Intent intent = new Intent(context, CodeEntry.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}