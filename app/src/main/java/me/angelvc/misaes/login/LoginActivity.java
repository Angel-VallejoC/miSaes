package me.angelvc.misaes.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    public ActivityLoginBinding binding;

    // TODO: check internet connection before requesting login page
    // TODO: create session when logging in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_preferences_key), Context.MODE_PRIVATE);

        // Setting up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavHost);
        NavController navController = navHostFragment.getNavController();

        String school = sharedPreferences.getString(getString(R.string.login_school_preference), null);
        if (school != null){
            navController.navigate(R.id.action_selectSchoolFragment_to_enterCredentialsFragments);
        }
    }

}
