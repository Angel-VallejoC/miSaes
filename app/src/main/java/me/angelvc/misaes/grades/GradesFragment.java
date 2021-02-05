package me.angelvc.misaes.grades;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.FragmentGradesBinding;
import me.angelvc.misaes.grades.contracts.GradesContracts;
import me.angelvc.saes.scraper.models.GradeEntry;


public class GradesFragment extends Fragment implements GradesContracts.View {

    private FragmentGradesBinding binding;
    private GradesContracts.Presenter presenter;


    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        binding = FragmentGradesBinding.bind(view);
        binding.gradesRecycler.setAdapter(new GradesAdapter());
        binding.gradesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new GradesPresenterImpl(this);
        presenter.load();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.stop();
    }

    // --------------------- VIEW METHODS -----------------------
    @Override
    public void showGrades(ArrayList<GradeEntry> grades) {
        binding.gradesRecycler.setAdapter(new GradesAdapter(grades));
        binding.gradesRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyGrades() {
        binding.noDataTitle.setVisibility(View.VISIBLE);
        binding.noDataImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(binding.getRoot(), R.string.grades_error, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.bottomNavigationView)
                .show();
    }
}