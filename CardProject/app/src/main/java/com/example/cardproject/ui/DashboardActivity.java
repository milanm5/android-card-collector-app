package com.example.cardproject.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cardproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private NavController navController;

    private BottomNavigationView bottomNavigationView;

    private AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cardproject.R.layout.activity_dashboard);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBN);

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.cardFragment, R.id.favoriteFragment, R.id.cartFragment, R.id.mapsFragment).build();

        NavigationUI.setupWithNavController(this.bottomNavigationView, this.navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void showLogoutDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.logout)
                .setMessage(R.string.logout_message)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.no_button, null)
                .create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (this.navController.getCurrentDestination().getId() == R.id.cardFragment) {
            showLogoutDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logoutBtn) {
            showLogoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}
