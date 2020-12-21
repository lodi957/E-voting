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

/**
 * Created by Dell on 7/13/2017.
 */

public class BackgroundGivingPassOption extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    String newp;

    BackgroundGivingPassOption(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {


        String reg_url ="http://";
        reg_url=reg_url.concat(User.getInstance().getIp());
        reg_url=reg_url.concat("/SE/PasswordAgainOption.php");
        String method = params[0];

        if (method.equals("givingPassOption")) {
            try {

                newp = params[1];
                String renewp = params[2];
                String u=User.getInstance().getName();


                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                String post_data = URLEncoder.encode("newp", "UTF-8") + "=" + URLEncoder.encode(newp, "UTF-8") + "&"
                        + URLEncoder.encode("renewp", "UTF-8") + "=" + URLEncoder.encode(renewp, "UTF-8") + "&"
                        + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(u, "UTF-8");

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
        alertDialog.setTitle("Password Status");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String result) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();}

        alertDialog.setMessage(result);
        alertDialog.show();
        //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();


        if(result.contains("Complete success"))
        {

            User.getInstance().setPass(newp);

            Toast.makeText(context,"Password Successfully Changed",Toast.LENGTH_SHORT).show();

            DataGetter data= new DataGetter(context);
            data.execute();


            Intent intent = new Intent(context, MainPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else if(!result.contains("Complete success"))
        {
            //   alertDialog.setMessage("Unsuccessful");
            //  alertDialog.show();
            Toast.makeText(context,"Unsuccessful ",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}