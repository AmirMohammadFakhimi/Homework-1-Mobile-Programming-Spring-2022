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

import edu.sharif.homework1.databinding.FragmentStudentPanelBinding;

public class StudentPanelFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentStudentPanelBinding binding;
    private MyRecyclerViewAdapter adapter;

    private String username;
    Student student;
    private ArrayList<String> classesName;

    {
        classesName = new ArrayList<>();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStudentPanelBinding.inflate(inflater, container, false);

        username = StudentPanelFragmentArgs.fromBundle(getArguments()).getUsername();
        student = (Student) User.getUserByUsername(username);
        ((MainActivity) getActivity()).setActionBarTitle("Welcome " + student.getFirstName() +
                " " + student.getLastName());

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Class> classes = student.getClasses();
        for (Class c : classes) {
            if (!classesName.contains(c.getName())) {
                classesName.add(c.getName());
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.student_classes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), classesName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        binding.studentSearchButton.setOnClickListener(view1 -> {
            String searchText = binding.searchClassName.getText().toString();
            if (searchText.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a class name", Toast.LENGTH_SHORT).show();
            } else if (!classesName.contains(searchText)) {
                Toast.makeText(getContext(), "Class not found", Toast.LENGTH_SHORT).show();
            } else {
                NavHostFragment.findNavController(StudentPanelFragment.this)
                        .navigate(StudentPanelFragmentDirections.
                                actionStudentPanelFragmentToClassPageFragment(searchText));
            }
        });

        Button addToClassButton = view.findViewById(R.id.add_to_class_button);
        addToClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StudentPanelFragment.this)
                        .navigate(StudentPanelFragmentDirections.
                                actionStudentPanelFragmentToAddToClassFragment(username));
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
        NavHostFragment.findNavController(StudentPanelFragment.this)
                .navigate(StudentPanelFragmentDirections.
                        actionStudentPanelFragmentToClassPageFragment(classesName.get(position)));
    }
}