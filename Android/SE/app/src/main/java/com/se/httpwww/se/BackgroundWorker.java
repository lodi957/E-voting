package com.se.httpwww.se;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * Created by Dell on 4/19/2017.
 */

public class BackgroundWorker extends AsyncTask<String, Void,String> {

    private ProgressDialog progressDialog;
    Context context;
    AlertDialog alertDialog;
    String user_name,password;

    // String userName;

    BackgroundWorker(Context ctx)
    {
        context=ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url ="http://";
        login_url=login_url.concat(User.getInstance().getIp());
        login_url=login_url.concat("/SE/LoginFile.php");
       // String login_url="http://192.168.8.104/SE/Login.php";

        if(type.equals("login"))
        {

                try {
                    user_name = params[1];
                    password = params[2];

                    if(internet_connection())
                    {

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                }
                else
                {
                    String result="Connection Error";
                    return result;
                }
            } catch (MalformedURLException e) {
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);

                alertDialog1.setTitle("Invalid");
                alertDialog1.setMessage("Please Enter Valid Credentials");
                alertDialog1.show();
                e.printStackTrace();
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);

                alertDialog2.setTitle("Invalid");
                alertDialog2.setMessage("Please Enter Valid Credentials");
                alertDialog2.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String result) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();}

        if(result==null || !result.contentEquals("login success"))
        {
            alertDialog.setTitle("Invalid");
               alertDialog.setMessage("Invalid CNIC or Password");
              alertDialog.show();
           // Toast.makeText(context,"Unsuccessful "+result+" "+user_name+" "+password,Toast.LENGTH_LONG).show();
        }

        else if(result.contentEquals("login success"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();

            User.getInstance().setName(user_name);
            User.getInstance().setPass(password);

            DataGetter data= new DataGetter(context);
            data.execute();


            Intent intent = new Intent(context, MainPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               //intent.putExtra("u",userName);
            context.startActivity(intent);


        }

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
