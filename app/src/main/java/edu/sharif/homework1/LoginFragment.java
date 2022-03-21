package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.sharif.homework1.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private String username;
    private String enteredPassword;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signUpButton = view.findViewById(R.id.button);
        signUpButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment());
        });

        binding.submitButton.setOnClickListener(view1 -> {

            username = binding.usernameText.getText().toString();
            enteredPassword = binding.passwordText.getText().toString();

            if (username.isEmpty()) {
                Toast.makeText(getContext(), "Please fill username field.",
                        Toast.LENGTH_LONG).show();
            } else {
                User user = User.getUserByUsername(username);

                if (user == null) {
                    Toast.makeText(getContext(), "there is no user with this username.",
                            Toast.LENGTH_LONG).show();
                } else if (!user.getPassword().equals(enteredPassword)) {
                    Toast.makeText(getContext(), "wrong username or password",
                            Toast.LENGTH_LONG).show();
                } else {
                    if(user.getClass().equals(Student.class))
                        openStudentPanelFragment();
                    else openProfessorPanelFragment();
                }
            }
        });
    }


    private void openStudentPanelFragment() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(LoginFragmentDirections.
                        actionLoginFragmentToStudentPanelFragment(username));
    }

    private void openProfessorPanelFragment() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(LoginFragmentDirections.
                                actionLoginFragmentToProfessorPanelFragment(username));
    }


}
