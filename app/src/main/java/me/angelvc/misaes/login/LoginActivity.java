package me.angelvc.misaes.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.ActivityLoginBinding;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.util.AppPreferences;
import me.angelvc.saes.scraper.SAEScraper;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    public ActivityLoginBinding binding;

    // TODO: check internet connection before requesting login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setting up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavHost);
        NavController navController = navHostFragment.getNavController();

        // check if session exists
        if ( AppPreferences.getLoginStatus(this) ) {
            try {
                SAEScraper scraper = AppPreferences.getScraperInstance(AppPreferences.getScraperJson(this));
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra(getString(R.string.app_scraper_instance), scraper);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                // if an exception occurs, it means the session expired
                // or there was a problem retrieving it, so, just continue with login
                AppPreferences.setLoginStatus(this, false);
                AppPreferences.deleteScraperInstance(this);
                e.printStackTrace();
            }
        }

        //check if a school has been selected to skip the select school step
        String school = AppPreferences.getSelectedSchool(this);
        if (school != null){
            navController.setGraph(R.navigation.login_nav_graph);
            navController.navigate(R.id.action_selectSchoolFragment_to_enterCredentialsFragments);
        }
    }

}
