package me.angelvc.misaes.me;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.FragmentMeBinding;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.me.contracts.MeContracts;
import me.angelvc.saes.scraper.models.StudentInfo;


public class MeFragment extends Fragment implements MeContracts.View {

    MeContracts.Presenter presenter;
    FragmentMeBinding binding;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        binding = FragmentMeBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MePresenterImpl(this, ((HomeActivity) getActivity()).scraper );
        presenter.load();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.stop();
    }

    // ------------------------------------ VIEW METHODS ------------------------------------
    @Override
    public void showInfo(StudentInfo info) {
        binding.schoolName.setText(info.getCampus());
        binding.student.setText(info.getName());
        binding.studentId.setText(info.getId());
        binding.degree.setText(info.getDegreeName());
        binding.plan.setText(info.getPlan());
        binding.avg.setText(info.getAverage());

        binding.schoolName.setVisibility(View.VISIBLE);
        binding.studentTitle.setVisibility(View.VISIBLE);
        binding.student.setVisibility(View.VISIBLE);
        binding.studentIdTitle.setVisibility(View.VISIBLE);
        binding.studentId.setVisibility(View.VISIBLE);
        binding.degreeTitle.setVisibility(View.VISIBLE);
        binding.degree.setVisibility(View.VISIBLE);
        binding.planTitle.setVisibility(View.VISIBLE);
        binding.plan.setVisibility(View.VISIBLE);
        binding.avgTitle.setVisibility(View.VISIBLE);
        binding.avg.setVisibility(View.VISIBLE);
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
        Snackbar.make(binding.getRoot(), R.string.me_error, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.bottomNavigationView)
                .show();
    }
}