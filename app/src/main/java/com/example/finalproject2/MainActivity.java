package com.example.finalproject2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button getNews;
    Button getFavs;
    Button submitEmailButton;
    EditText submitEmail;
    private ProgressBar pgsBar;
    private int i = 0;
    private Handler hdlr = new Handler();
    String validate;
    SharedPreferences mSharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        getFavs = (Button) findViewById(R.id.button2);
        getNews = (Button) findViewById(R.id.button);
        submitEmailButton = (Button) findViewById(R.id.button3);
        submitEmail = (EditText) findViewById(R.id.enterEmail);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        Snackbar.make(getWindow().getDecorView().getRootView(), "Connected to BBC Database", Snackbar.LENGTH_LONG).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());



        getNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewsActivity();
            }
        });

        getFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavsActivity();
            }
        });

        submitEmailButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (submitEmail.getText().toString() != ""){
                submitEmail.setText("Email Submitted");
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("maxDead", submitEmail.getText().toString());
                    editor.commit();
                    pgsBar.incrementProgressBy(100);
                Toast.makeText(MainActivity.this, "Email Submitted", Toast.LENGTH_SHORT).show();
                }
                else{
                    submitEmail.setText("Enter an Email");
                    Toast.makeText(MainActivity.this, "Submission Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            if(item.getItemId() == R.id.nav_home){
                Toast.makeText(MainActivity.this, "You are already on the home page.", Toast.LENGTH_SHORT).show();
            }


            else if (item.getItemId() == R.id.nav_news){
                openNewsActivity();
            }

            else if (item.getItemId() == R.id.nav_favs){
                openFavsActivity();
            }

            else if (item.getItemId() == R.id.nav_help){
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Help");
                alertDialog.setMessage("Use the left hand navigation to go to the page you desire. In order to save news items to your favorites, click the favorite icon next to the specified article. Click the favorite item to remove favorites from your list.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    });


    }

            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openNewsActivity(){
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

    public void openFavsActivity(){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

}