package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sharif.homework1.databinding.FragmentCreateClassBinding;

public class CreateClassFragment extends Fragment{

    private FragmentCreateClassBinding binding;

    private String newClassName;
    private String username;
    private Professor professor;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCreateClassBinding.inflate(inflater, container, false);
        username = CreateClassFragmentArgs.fromBundle(getArguments()).getProfessorUserName();
        professor = (Professor) User.getUserByUsername(username);
        ((MainActivity) getActivity()).setActionBarTitle("Create class");

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText className = view.findViewById(R.id.className_text);


        binding.submitButton.setOnClickListener(view1 -> {

            newClassName = className.getText().toString();

            if (newClassName.isEmpty()) {
                Toast.makeText(getContext(), "Please fill class name field.",
                        Toast.LENGTH_LONG).show();
            } else if (Class.getClassByName(newClassName) != null) {
                Toast.makeText(getContext(), "Class with this name already exists.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "class " + newClassName + " created successfully.",
                        Toast.LENGTH_LONG).show();
                createClass(newClassName);
            }
        });
    }

    private void createClass(String className) {
        Class c = new Class(className, professor.getUsername(), getActivity());
        professor.addClass(c, getActivity(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
