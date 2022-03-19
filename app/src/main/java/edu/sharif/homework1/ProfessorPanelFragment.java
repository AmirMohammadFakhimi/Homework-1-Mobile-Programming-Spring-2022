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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.homework1.databinding.FragmentProfessorPanelBinding;

public class ProfessorPanelFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentProfessorPanelBinding binding;
    private MyRecyclerViewAdapter adapter;

    private String username;
    Professor professor;
    private ArrayList<String> classesName;

    {
        classesName = new ArrayList<>();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentProfessorPanelBinding.inflate(inflater, container, false);

        username = ProfessorPanelFragmentArgs.fromBundle(getArguments()).getUsername();
        professor = (Professor) User.getUserByUsername(username);
        ((MainActivity) getActivity()).setActionBarTitle("Welcome " + professor.getFirstName() +
                " " + professor.getLastName());

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Class> classes = professor.getClasses();
        for (Class c : classes) {
            if (!classesName.contains(c.getName())){
                classesName.add(c.getName());
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.professor_classes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), classesName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        binding.professorSearchButton.setOnClickListener(view1 -> {
            String searchText = binding.searchClassName.getText().toString();
            if (searchText.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a class name", Toast.LENGTH_SHORT).show();
            } else if (!classesName.contains(searchText)) {
                Toast.makeText(getContext(), "Class not found", Toast.LENGTH_SHORT).show();
            } else {
                NavHostFragment.findNavController(ProfessorPanelFragment.this)
                        .navigate(ProfessorPanelFragmentDirections.
                                actionProfessorPanelFragmentToClassPageFragment(searchText));
            }
        });

        Button createClassButton = view.findViewById(R.id.create_class_button);
        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfessorPanelFragment.this)
                        .navigate(ProfessorPanelFragmentDirections.
                                actionProfessorPanelFragmentToCreateClassFragment(username));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        NavHostFragment.findNavController(ProfessorPanelFragment.this)
                .navigate(ProfessorPanelFragmentDirections.
                        actionProfessorPanelFragmentToClassPageFragment(classesName.get(position)));
    }
}