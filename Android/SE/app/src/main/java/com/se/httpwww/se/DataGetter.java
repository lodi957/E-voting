package com.se.httpwww.se;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class DataGetter extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    String address;
    InputStream is=null;
    String line=null;
    String result=null;

    DataGetter(Context ctx) {
        context = ctx;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {


        try{
            address = "http://";
            address=address.concat(User.getInstance().getIp());
            address=address.concat("/SE/DataGetterFile.php");

            String user_name= User.getInstance().getName();
            String password= User.getInstance().getPass();

            URL url=new URL(address);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");


            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


            String post_data= URLEncoder.encode("user_name" ,"UTF-8")+"="+URLEncoder.encode(user_name ,"UTF-8")+"&"+URLEncoder.encode("password" ,"UTF-8")+"="+URLEncoder.encode(password ,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();



            is=new BufferedInputStream(con.getInputStream());


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try{

            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();

            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //PARSE JSON DATA

        try
        {


            JSONObject jo=new JSONObject(result);

            User.getInstance().setContact(jo.getString("contact"));
            User.getInstance().setFullname(jo.getString("FullName"));



        } catch (JSONException e) {
            e.printStackTrace();

        }

        return null;
    }


    protected void onPreExecute() {

//        progressDialog.setMessage("Please Wait...");
//        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String result) {

//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();}

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}