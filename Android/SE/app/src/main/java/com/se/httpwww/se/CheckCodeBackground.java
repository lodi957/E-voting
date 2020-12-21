package com.se.httpwww.se;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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


public class CheckCodeBackground extends AsyncTask<String,Void,String>{
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    CheckCodeBackground(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {


        String reg_url ="http://";
        reg_url=reg_url.concat(User.getInstance().getIp());
        reg_url=reg_url.concat("/SE/CodeChecking.php");

        String method = params[0];

        if (method.equals("checkCode")) {
            try {

                String code = params[1];


                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String e=User.getInstance().getTempSignUpUserName();

                String post_data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")
                        +"&"+URLEncoder.encode("e" ,"UTF-8")+"="+URLEncoder.encode(e ,"UTF-8");
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

        if(result.contains("Login"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();

            User.getInstance().setName(User.getInstance().getTempSignUpUserName());

            Intent intent = new Intent(context, GivingPassOption.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else if(!result.contains("Login"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();
            Toast.makeText(context,"Invalid Code",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}