package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.sharif.homework1.databinding.FragmentStudentSignupBinding;

public class StudentSignupFragment extends Fragment {

    private FragmentStudentSignupBinding binding;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String studentNumber;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStudentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getUsername();
        password = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getPassword();
        firstName = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getFirstName();
        lastName = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getLastName();

        binding.submitButton.setOnClickListener(view1 -> {
            studentNumber = binding.studentText.getText().toString();

            if (studentNumber.isEmpty()) {
                Toast.makeText(getContext(), "Please fill student number field.",
                        Toast.LENGTH_LONG).show();
            } else {
                User user = User.getUserByUsername(username);

                if (user == null) {
                    new Student(username, password, firstName, lastName, studentNumber);
                    openStudentPanelFragment();
                } else if (!((Student) user).getStudentNumber().equals(studentNumber)) {
                    Toast.makeText(getContext(), "You are already registered with another " +
                                    "student number", Toast.LENGTH_LONG).show();
                } else {
                    openStudentPanelFragment();
                }
            }
        });
    }

    private void openStudentPanelFragment() {
        NavHostFragment.findNavController(StudentSignupFragment.this)
                .navigate(StudentSignupFragmentDirections.
                        actionStudentSignupToStudentPanel(username));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}