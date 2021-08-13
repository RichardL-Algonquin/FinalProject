package com.example.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView lvNews;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvNews = (ListView) findViewById(R.id.lvNews);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        new ProcessBackground().execute();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    openHomeActivity();
                }

                else if (item.getItemId() == R.id.nav_news){
                    Toast.makeText(NewsActivity.this, "You are already on the news page.", Toast.LENGTH_SHORT).show();
                }

                else if (item.getItemId() == R.id.nav_favs){
                    openFavsActivity();
                }

                else if (item.getItemId() == R.id.nav_help){
                    AlertDialog alertDialog = new AlertDialog.Builder(NewsActivity.this).create();
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

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
    public class ProcessBackground extends AsyncTask<Integer, Void, Exception>{


        ProgressDialog pd = new ProgressDialog(NewsActivity.this);
        Exception exception = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Loading RSS Feed, please wait");
            pd.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {
            try{
                URL url = new URL ("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url),"UTF_8");

                boolean insideItem = false;

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG){
                        if (xpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title")){
                            if (insideItem){
                                titles.add(xpp.nextText());
                            }
                        }

                        else if (xpp.getName().equalsIgnoreCase("link")){
                            if (insideItem){
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }




            }
            catch(MalformedURLException e){
                exception = e;
            }

            catch (XmlPullParserException e){
                exception = e;
            }

            catch (IOException e){
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, titles);
            lvNews.setAdapter(adapter);

            pd.dismiss();
        }
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

    public void openHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openFavsActivity(){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}