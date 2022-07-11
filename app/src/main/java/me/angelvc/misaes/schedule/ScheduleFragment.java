package me.angelvc.misaes.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.FragmentScheduleBinding;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.schedule.contracts.ScheduleContracts;
import me.angelvc.misaes.util.Cache;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleFragment extends Fragment implements ScheduleContracts.View {

    private FragmentScheduleBinding binding;
    private ScheduleContracts.Presenter presenter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        binding = FragmentScheduleBinding.bind(view);
        binding.scheduleRecycler.setAdapter(new ScheduleAdapter());
        binding.scheduleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.swipeRefresh.setOnRefreshListener(() ->{
            Cache.delete(Cache.Type.SCHEDULE, getContext());
            if (presenter != null)
                presenter.load();
            binding.swipeRefresh.setRefreshing(false);

            Snackbar.make(binding.getRoot(), R.string.refresh_success, BaseTransientBottomBar.LENGTH_SHORT)
                    .setAnchorView(R.id.bottomNavigationView)
                    .show();
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new SchedulePresenterImpl(this, ((HomeActivity) getActivity()).scraper );
        presenter.load();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.stop();
    }

    // --------------------  VIEW METHODS --------------------
    @Override
    public void showSchedule(List<ScheduleClass> schedule) {
        binding.scheduleRecycler.setAdapter(new ScheduleAdapter(schedule));
        binding.scheduleRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptySchedule() {
        binding.noDataImg.setVisibility(View.VISIBLE);
        binding.noDataTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        Snackbar.make(binding.getRoot(), R.string.schedule_error, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.bottomNavigationView)
                .show();
    }
}