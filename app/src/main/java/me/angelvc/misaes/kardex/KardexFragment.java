package me.angelvc.misaes.kardex;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.FragmentKardexBinding;
import me.angelvc.misaes.kardex.contracts.KardexContracts;
import me.angelvc.saes.scraper.models.Kardex;


public class KardexFragment extends Fragment implements KardexContracts.View {

    private KardexContracts.Presenter presenter;
    private FragmentKardexBinding binding;

    public KardexFragment() {
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
        View view = inflater.inflate(R.layout.fragment_kardex, container, false);
        binding = FragmentKardexBinding.bind(view);
        binding.kardexGradesRecycler.setAdapter(new KardexAdapter());
        binding.kardexGradesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new KardexPresenterImpl(this);
        presenter.load();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.stop();
    }

    // --------------------- VIEW METHODS ---------------------
    @Override
    public void showKardexGrades(Kardex kardex) {
        binding.kardexGradesRecycler.setAdapter(new KardexAdapter(kardex));
        binding.kardexGradesRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyKardex() {
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
    public void showError(){
        Snackbar.make(binding.getRoot(), R.string.kardex_error, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.bottomNavigationView)
                .show();
    }
}