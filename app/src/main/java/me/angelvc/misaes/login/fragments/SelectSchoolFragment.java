package me.angelvc.misaes.login.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.LoginFragmentSelectSchoolBinding;


public class SelectSchoolFragment extends Fragment {

    private LoginFragmentSelectSchoolBinding binding;

    public SelectSchoolFragment() {
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
        View view = inflater.inflate(R.layout.login_fragment_select_school, container, false);
        binding =  LoginFragmentSelectSchoolBinding.bind(view);
        binding.nextButton.setOnClickListener((v) -> {
            getActivity()
                    .getSharedPreferences(getString(R.string.app_preferences_key), Context.MODE_PRIVATE)
                    .edit()
                    .putString(getString(R.string.login_school_preference), binding.school.getSelectedItem().toString())
                    .apply();
            Navigation.findNavController(v).navigate(R.id.action_selectSchoolFragment_to_enterCredentialsFragments);
        });
        setToggleListener();
        return view;
    }

    private void setToggleListener() {
        binding.toggle.setOnCheckedChangeListener((group, checkedId) -> binding.school.setAdapter(
                new ArrayAdapter<>(getActivity(), R.layout.login_spinner_item,
                        R.id.university == checkedId
                                ? getResources().getStringArray(R.array.universities)
                                : getResources().getStringArray(R.array.highschools))
        ));
        binding.toggle.check(R.id.university);
        binding.school.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.login_spinner_item, getResources().getStringArray(R.array.universities)));
    }
}