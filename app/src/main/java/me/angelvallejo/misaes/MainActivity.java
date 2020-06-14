package me.angelvallejo.misaes;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import me.angelvallejo.misaes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
                          implements GetContent.GetContentListener{
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    // TODO: check internet connection before requesting login page
    // TODO: create session when logging in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateForm()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    new GetContent(MainActivity.this)
                            .execute(
                                    GetContent.Action.LOGIN,
                                    binding.registrationId.getText().toString(),
                                    binding.password.getText().toString(),
                                    binding.captcha.getText().toString()
                            );
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.progressBar.setVisibility(View.VISIBLE);
        new GetContent(this).execute(GetContent.Action.LOAD_LOGIN);
    }

    @Override
    public <T> void onResultReady(GetContent.Action action, T result) {
        if (binding.progressBar.getVisibility() == View.VISIBLE)
            binding.progressBar.setVisibility(View.INVISIBLE);

        switch (action){
            case LOAD_LOGIN:
                byte[] captchaResponse = (byte[]) result;
                binding.captchaImage.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                                captchaResponse,0,captchaResponse.length
                        )
                );
                return;

            case LOGIN:
                boolean isLoggedIn = (boolean) ((Pair) result).first;
                if (isLoggedIn){
                    startActivity(new Intent(this, TempActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else {
                    String errorMessage = (String) ((Pair) result).second;
                    Snackbar.make(binding.rootView, errorMessage, Snackbar.LENGTH_LONG).show();
                    // when the login is not successful, login page must be refreshed
                    binding.progressBar.setVisibility(View.VISIBLE);
                    new GetContent(this).execute(GetContent.Action.LOAD_LOGIN);
                }
        }
    }

    private boolean validateForm() {
        boolean isValid = isFieldEmpty(binding.registrationId, binding.registrationIdLayout) &&
                            isFieldEmpty(binding.password, binding.passwordLayout) &&
                            isFieldEmpty(binding.captcha, binding.captchaLayout);
        return isValid;
    }

    private boolean isFieldEmpty(TextInputEditText editText, TextInputLayout inputLayout){
        if (editText.getText().toString().trim().length() == 0) {
            inputLayout.setError(getString(R.string.error_field_required));
            return false;
        }
        else
            inputLayout.setError(null);

        return true;
    }
}
