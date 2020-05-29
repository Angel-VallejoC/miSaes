package me.angelvallejo.misaes;

import androidx.appcompat.app.AppCompatActivity;
import me.angelvallejo.misaes.databinding.ActivityMainBinding;

import android.graphics.BitmapFactory;
import android.os.Bundle;

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
        new GetContent(this).execute(GetContent.Action.LOAD_LOGIN);
    }

    @Override
    public <T> void onResultReady(GetContent.Action action, T result) {
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
