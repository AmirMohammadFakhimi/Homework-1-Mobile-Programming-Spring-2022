package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.homework1.databinding.FragmentAddToClassBinding;

public class AddToClassFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    private FragmentAddToClassBinding binding;
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

        binding = FragmentAddToClassBinding.inflate(inflater, container, false);

        username = StudentPanelFragmentArgs.fromBundle(getArguments()).getUsername();
        student = (Student) User.getUserByUsername(username);

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Class> classes = Class.classes;
        for (Class c : classes) {
            if (!student.getClasses().contains(c)) {
                classesName.add(c.getName());
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.classes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), classesName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        binding.addClass.setOnClickListener(view1 -> {
            String searchText = binding.className.getText().toString();
            if (searchText.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a class name", Toast.LENGTH_SHORT).show();
            } else if (!classesName.contains(searchText)) {
                Toast.makeText(getContext(), "Class not found", Toast.LENGTH_SHORT).show();
            } else {
                addToClass(searchText);
            }
        });
    }

    private void addToClass(String searchText) {
        Class newClass = Class.getClassByName(searchText);
        newClass.getStudents().add(student);
        student.getClasses().add(newClass);
        Toast.makeText(getContext(), "you added to this class!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(View view, int position) {
        Class newClass = Class.classes.get(position);
        newClass.getStudents().add(student);
        student.getClasses().add(newClass);
        Toast.makeText(getContext(), "you added to this class!", Toast.LENGTH_SHORT).show();
    }
}
