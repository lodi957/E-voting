package com.se.httpwww.se;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainPage extends ListActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,email;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        String a = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", User.getInstance().getName());
        editor.putString("password", User.getInstance().getPass());
        editor.apply();

        String method = "code";
        if (internet_connection()) {
            BackgroundCode backgroundTask = new BackgroundCode(this);
            backgroundTask.execute(method, a);
        } else {
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.yellow));
            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //recheck internet connection and call DownloadJson if there is internet
                }
            }).show();
        }

        DataGetter data= new DataGetter(this);
        data.execute();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.inflateHeaderView(R.layout.nav_header_main_page);

        name = (TextView)header.findViewById(R.id.naam);
        iv= (ImageView)header.findViewById(R.id.imageView);
        email = (TextView)header.findViewById(R.id.textView);
        name.setText(User.getInstance().getFullname());
        email.setText(User.getInstance().getName());
        iv.setImageResource(R.drawable.logo);

        BackgroundGetMainPageEvents bgmpe= new BackgroundGetMainPageEvents(this);
        bgmpe.execute();

        MainPageView MyAdapter = new MainPageView(this, User.getInstance().votingEventsList);
        setListAdapter(MyAdapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_home)
        {


        }else if (id == R.id.nav_notification) {
            BackgroundGetVotingResults bgr= new BackgroundGetVotingResults(this);
            bgr.execute();

        } else if (id == R.id.votingpage) {

            BackgroundGetVotingEvents bgve= new BackgroundGetVotingEvents(this);
            bgve.execute();


    }
        else if (id == R.id.nav_settings) {
            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);


        }else if (id == R.id.nav_help) {
            showHelp();
        } else if (id == R.id.nav_about) {
            showAbout();
        }else if (id == R.id.nav_logout) {

            User.getInstance().myClear();

            SharedPreferences sharedPref=getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
            sharedPref.edit().clear().apply();

            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAbout()
    {
        AlertDialog.Builder myAlert=new AlertDialog.Builder(this);
        myAlert.setMessage("Contact us:" +
                "\n\n"+"03323349909\n\n"+"Email us:\n\n contactus@ezsaman.com").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();;
            }

        }).create();
        myAlert.show();
    }



    public void showHelp()
    {
        AlertDialog.Builder myAlert=new AlertDialog.Builder(this);
        myAlert.setMessage("For Help Visit Our Page:" +
                "\n\n"+"https://www.facebook.com/ezsaman/\n" +
                "https://www.instagram.com/ezsamandelivery/").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();;
            }

        }).create();
        myAlert.show();
    }


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
