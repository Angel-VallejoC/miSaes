package me.angelvallejo.misaes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import me.angelvallejo.misaes.databinding.ActivityAlumnoBinding;

public class AlumnoActivity extends AppCompatActivity {

    private ActivityAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}