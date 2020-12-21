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

/**
 * Created by Dell on 4/25/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    String user_name,email,user_pass,mob,fullname;
    private ProgressDialog progressDialog;
    BackgroundTask(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url ="http://";
        reg_url=reg_url.concat(User.getInstance().getIp());
        reg_url=reg_url.concat("/SE/SignUpFile.php");
        //String reg_url = "http://192.168.8.104/SE/SignUp.php";


        String method = params[0];

        if (method.equals("register")) {
            try {

                fullname = params[1];
                user_name = params[2];
                user_pass = params[3];
                mob = params[4];
                email = params[5];

                //   String cnic = params[6];

                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post_data = URLEncoder.encode("fullname", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8") + "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("mob", "UTF-8") + "=" + URLEncoder.encode(mob, "UTF-8");

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
        alertDialog.setTitle("Registration Status");

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String result) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();}

        if(result.contains("Complete Success"))
        {
          //  alertDialog.setMessage();
           // alertDialog.show();

            AlertDialog.Builder myAlert=new AlertDialog.Builder(context);
            myAlert.setMessage("SuccessFully Registered").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){

                    User.getInstance().setFullname(fullname);
                    User.getInstance().setName(user_name);
                    User.getInstance().setPass(user_pass);
                    User.getInstance().setContact(mob);

                    Intent intent = new Intent(context, MainPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }).create();
            myAlert.show();

            User.getInstance().setName(user_name);
            User.getInstance().setPass(user_pass);
            User.getInstance().setContact(mob);



        }
        else if(!result.contains("Complete Success"))
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            alertDialog.setTitle("Invalid");

            alertDialog.setMessage(result);

            alertDialog.show();

            AlertDialog.Builder myAlert=new AlertDialog.Builder(context);
            myAlert.setTitle("Invalid").setMessage("The email address in invalid").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){


                }

            }).create();
            myAlert.show();

        }


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}