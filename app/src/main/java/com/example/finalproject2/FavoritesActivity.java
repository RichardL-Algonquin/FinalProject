package com.example.finalproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_favorties);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    openHomeActivity();
                }

                else if (item.getItemId() == R.id.nav_news){
                    openNewsActivity();
                }

                else if (item.getItemId() == R.id.nav_favs){
                    Toast.makeText(FavoritesActivity.this, "You are already on the favorites page.", Toast.LENGTH_SHORT).show();
                }

                else if (item.getItemId() == R.id.nav_help){
                    AlertDialog alertDialog = new AlertDialog.Builder(FavoritesActivity.this).create();
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

    public void openHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openFavsActivity(){
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}