package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sharif.homework1.databinding.FragmentCreateTrainingBinding;

public class CreateTrainingFragment extends Fragment{

    private FragmentCreateTrainingBinding binding;

    private String className;
    private String newTrainingName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCreateTrainingBinding.inflate(inflater, container, false);
        className = CreateTrainingFragmentArgs.fromBundle(getArguments()).getClassName();
        ((MainActivity) getActivity()).setActionBarTitle("Add Training to class " + className + ".");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText trainingName = view.findViewById(R.id.trainingName_text);


        binding.submitButton.setOnClickListener(view1 -> {

            newTrainingName = trainingName.getText().toString();

            if (newTrainingName.isEmpty()) {
                Toast.makeText(getContext(), "Please fill training name field.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "training " + newTrainingName + " added to class "
                        + className + " successfully.", Toast.LENGTH_LONG).show();
                addTrainingToClass(newTrainingName);
            }
        });
    }

    private void addTrainingToClass(String trainingName) {
        Class ownerClass = Class.getClassByName(className);
        ownerClass.getTrainings().add(new Training(trainingName, className, getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
