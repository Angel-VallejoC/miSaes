package me.angelvc.misaes.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import me.angelvc.misaes.util.AppPreferences;
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

        Log.d("debug", "HomeActivity onCreate");

        // if the user was previously logged in, the scraper will be retrieved from shared preferences
        // if the user just logged in, scraper can be obtained with its getInstance method
        Intent intent = getIntent();
        if (intent.getSerializableExtra(getString(R.string.app_scraper_instance)) != null) {
            try {
                scraper = AppPreferences.getScraperInstance(AppPreferences.getScraperJson(this));
            } catch (Exception e) {
                logout();
            }
        }
        else {
            scraper = SAEScraper.getInstance(School.getSchoolByName(AppPreferences.getSelectedSchool(this)));
        }

        // Setting up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavHost);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.meFragment ,R.id.gradesFragment, R.id.kardexFragment, R.id.scheduleFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        Log.d("debug", "HomeActivity onCreate:  ends");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scraper != null)
            AppPreferences.saveScraperInstance(this, scraper);
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

    public void logout() {
        AppPreferences.setLoginStatus(this, false);
        AppPreferences.deleteScraperInstance(this);

        startActivity(new Intent(this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}