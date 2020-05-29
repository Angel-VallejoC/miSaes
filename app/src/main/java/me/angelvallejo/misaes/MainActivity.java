package me.angelvallejo.misaes;

import androidx.appcompat.app.AppCompatActivity;
import me.angelvallejo.misaes.databinding.ActivityMainBinding;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
                          implements GetContent.GetContentListener{
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
        }
    }
}
