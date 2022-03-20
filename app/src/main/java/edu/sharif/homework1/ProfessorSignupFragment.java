package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import edu.sharif.homework1.databinding.FragmentProfessorSignupBinding;

public class ProfessorSignupFragment extends Fragment {

    private FragmentProfessorSignupBinding binding;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String university;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentProfessorSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getUsername();
        password = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getPassword();
        firstName = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getFirstName();
        lastName = ProfessorSignupFragmentArgs.fromBundle(getArguments()).getLastName();

        binding.submitButton.setOnClickListener(view1 -> {
            university = binding.universityText.getText().toString();

            if (university.isEmpty()) {
                Toast.makeText(getContext(), "Please fill university field.", Toast.LENGTH_LONG)
                        .show();
            } else {
                User user = User.getUserByUsername(username);

                if (user == null) {
                    new Professor(username, password, firstName, lastName, university, getActivity());
                    openProfessorPanelFragment();
                } else if (!((Professor) user).getUniversity().equals(university)) {
                    Toast.makeText(getContext(), "You are already registered to another university",
                            Toast.LENGTH_LONG).show();
                } else {
                    openProfessorPanelFragment();
                }
            }
        });
    }

    private void openProfessorPanelFragment() {
        NavHostFragment.findNavController(ProfessorSignupFragment.this)
                .navigate(ProfessorSignupFragmentDirections.
                        actionProfessorSignupToProfessorPanel(username));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}