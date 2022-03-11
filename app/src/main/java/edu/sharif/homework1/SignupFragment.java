package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.sharif.homework1.databinding.FragmentSignupBinding;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;

    private String username;
    private String enteredPassword;
    private String firstName;
    private String lastName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.professorButton.setOnClickListener(view1 -> {
            if (areFieldsValid(false)) {
                openSignupProfessorFragment();
            }
        });

        binding.studentButton.setOnClickListener(view1 -> {
            if (areFieldsValid(true)) {
                openSignupStudentFragment();
            }
        });
    }

    private boolean areFieldsValid(boolean isStudentClicked) {
        username = binding.usernameText.getText().toString();
        enteredPassword = binding.passwordText.getText().toString();
        firstName = binding.firstNameText.getText().toString();
        lastName = binding.lastNameText.getText().toString();

        if (username.isEmpty() || enteredPassword.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty()) {

            Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
            return false;
        } else if (User.getUserByUsername(username) != null) {

            User user = User.getUserByUsername(username);
            String usernamePassword = user.getPassword();
            if (!enteredPassword.equals(usernamePassword)) {
                Toast.makeText(getContext(), "Wrong password.", Toast.LENGTH_LONG).show();
                return false;
            } else if (isStudentClicked && !user.getClass().equals(Student.class)) {
                Toast.makeText(getContext(), "You aren't a student!!", Toast.LENGTH_LONG)
                        .show();
                return false;
            } else if (!isStudentClicked && !user.getClass().equals(Professor.class)) {
                Toast.makeText(getContext(), "You aren't a professor!!", Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }

        return true;
    }

    private void openSignupProfessorFragment() {
        NavHostFragment.findNavController(SignupFragment.this)
                .navigate(SignupFragmentDirections.actionSignupToProfessor(username,
                        enteredPassword, firstName, lastName));
    }

    private void openSignupStudentFragment() {
        NavHostFragment.findNavController(SignupFragment.this)
                .navigate(SignupFragmentDirections.actionSignupToStudent(username,
                        enteredPassword, firstName, lastName));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}