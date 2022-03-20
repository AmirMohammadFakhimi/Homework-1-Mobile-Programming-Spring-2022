package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.homework1.databinding.FragmentCreateClassBinding;
import edu.sharif.homework1.databinding.FragmentCreateTrainingBinding;
import edu.sharif.homework1.databinding.FragmentStudentSignupBinding;

public class CreateTrainingFragment extends Fragment{

    private FragmentCreateTrainingBinding binding;

    private String className;
    private String professorUsername;
    private String newTrainingName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCreateTrainingBinding.inflate(inflater, container, false);
        professorUsername = CreateTrainingFragmentArgs.fromBundle(getArguments()).getProfessorUsername();
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
        Class ownerClass = Class.getClassByClassName(professorUsername, className);
        ownerClass.getTrainings().add(new Training(trainingName, ownerClass));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
