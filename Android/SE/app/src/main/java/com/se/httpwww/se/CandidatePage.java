package com.se.httpwww.se;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ArrayAdapter;

import java.io.InputStream;

/**
 * Created by Dell on 4/7/2018.
 */

public class CandidatePage extends AppCompatActivity {


    ArrayAdapter<String> adapter;

    InputStream is=null;
    String line=null;
    String result=null;


    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates);



        RecyclerView list=(RecyclerView)findViewById(R.id.grid);
        sv=(SearchView)findViewById(R.id.mSearch);
        //list.setLayoutManager(new LinearL)
        //list.setLayoutManager(new GridLayoutManager(this,3));

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //getData();


        final CustomCandidateRecyclerAdapter adapter = new CustomCandidateRecyclerAdapter(User.getInstance().candidatesList,this);
        list.setLayoutManager(new GridLayoutManager(this,2));
        list.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });

    }




//    private void getData()
//    {
//        try{
//
//            String address ="http://";
//            address=address.concat(Info.getInstance().getIp());
//            address=address.concat("/SE/GetCandidates.php");
//
//            URL url=new URL(address);
//            HttpURLConnection con=(HttpURLConnection)url.openConnection();
//
//            con.setRequestMethod("POST");
//            is=new BufferedInputStream(con.getInputStream());
//
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        try{
//
//            BufferedReader br=new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb=new StringBuilder();
//
//            while((line=br.readLine())!=null)
//            {
//                sb.append(line+"\n");
//            }
//            is.close();
//            result=sb.toString();
//
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        //PARSE JSON DATA
//
//        try
//        {
//            JSONArray ja=new JSONArray(result);
//            JSONObject jo=null;
//
//            String id,name,eid,pic;
//
//            Info.getInstance().candidatesList.clear();
//
//            for(int i=0;i<ja.length();i++)
//            {
//                id=jo.getString("id");
//                name=jo.getString("name");
//                eid=jo.getString("event_id");
//                pic=jo.getString("profile_pic");
//
//
//                Info.getInstance().candidatesList.add(new CandidatesClass(id,name,eid,pic));
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}