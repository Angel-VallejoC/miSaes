package me.angelvc.misaes.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.ActivityHomeBinding;
import me.angelvc.misaes.login.LoginActivity;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.School;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    public SAEScraper scraper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        String school = getSharedPreferences(getString(R.string.app_preferences_key), Context.MODE_PRIVATE)
                .getString(getString(R.string.login_school_preference), null);
        scraper = SAEScraper.getInstance(School.getSchoolByName(school));

        // Setting up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavHost);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.meFragment ,R.id.gradesFragment, R.id.kardexFragment, R.id.scheduleFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout){
            showLogoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this, R.style.CustomDialogTheme);
        builder.setTitle("Cerrar sesión")
                .setMessage("¿Está seguro que desea cerrar sesión?")
                .setPositiveButton("Cerrar sesión", (dialogInterface, i) -> {
                    logout();
                })
                .setNegativeButton("Cancelar", ((dialogInterface, i) -> { }))
        ;
        builder.create().show();
    }

    private void logout() {
        startActivity(new Intent(this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}