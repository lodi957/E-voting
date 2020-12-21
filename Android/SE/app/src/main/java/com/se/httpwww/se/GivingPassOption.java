package com.se.httpwww.se;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GivingPassOption extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText newpass, renewpass;
    TextView name,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giving_pass_option);

        newpass=(EditText)findViewById(R.id.newPass);
        renewpass=(EditText)findViewById(R.id.reNewPass);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.inflateHeaderView(R.layout.nav_header_main_page);
//       //View header=navigationView.findViewById(R.layout.nav_header_navigation);
//
        name = (TextView)header.findViewById(R.id.naam);
        email = (TextView)header.findViewById(R.id.textView);
        name.setText(User.getInstance().getFullname());
        email.setText(User.getInstance().getName());

    }


    public void SubmitPass(View view)
    {
        String newp=newpass.getText().toString();
        String renewp=renewpass.getText().toString();

        if(newp.equals(renewp))
        {
            if(newp.length()>7 && newp.length()< 30) {
                String method = "givingPassOption";

                if (internet_connection()) {
                    BackgroundGivingPassOption changePass = new BackgroundGivingPassOption(this);
                    changePass.execute(method, newp, renewp);
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
            }
            else
            {
                AlertDialog.Builder myAlert=new AlertDialog.Builder(this);
                myAlert.setMessage("Please Enter Password Length atleast 8 Characters").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();;
                    }

                }).create();
                myAlert.show();

            }
        }
        else{
            AlertDialog.Builder myAlert=new AlertDialog.Builder(this);
            myAlert.setMessage("Passwords Not Same").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();;
                }

            }).create();
            myAlert.show();
        }
    }



    @Override
    public void onBackPressed() {

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
            Intent i = new Intent(getApplicationContext(), MyNotifications.class);
            startActivity(i);


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
