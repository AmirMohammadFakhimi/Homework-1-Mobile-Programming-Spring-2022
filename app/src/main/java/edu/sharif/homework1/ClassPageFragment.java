package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sharif.homework1.databinding.FragmentClassPageBinding;

public class ClassPageFragment extends Fragment {

    private FragmentClassPageBinding binding;

    private String className;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentClassPageBinding.inflate(inflater, container, false);

        className = ClassPageFragmentArgs.fromBundle(getArguments()).getClassName();
        ((MainActivity) getActivity()).setActionBarTitle("Class " + className);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}