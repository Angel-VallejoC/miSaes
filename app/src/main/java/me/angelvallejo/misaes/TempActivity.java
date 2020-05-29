package me.angelvallejo.misaes;

import androidx.appcompat.app.AppCompatActivity;
import me.angelvallejo.misaes.databinding.ActivityTempBinding;
import me.angelvallejo.misaes.parser.models.StudentInfo;

import android.os.Bundle;

public class TempActivity extends AppCompatActivity
                          implements GetContent.GetContentListener {


    private ActivityTempBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new GetContent(this).execute(GetContent.Action.GET_STUDENT_INFO);
    }

    @Override
    public <T> void onResultReady(GetContent.Action action, T result) {
        StudentInfo studentInfo = (StudentInfo) result;
        binding.name.setText(studentInfo.getNombre());
        binding.carrera.setText(studentInfo.getCarrera());
        binding.promedio.setText(studentInfo.getPromedio());
    }
}
