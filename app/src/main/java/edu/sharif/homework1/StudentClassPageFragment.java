package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.homework1.databinding.FragmentClassPageBinding;
import edu.sharif.homework1.databinding.FragmentStudentClassPageBinding;

public class StudentClassPageFragment extends Fragment  implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentStudentClassPageBinding binding;

    private String studentUsername;
    private String professorUsername;
    private String className;
    private Class thisClass;

    private ArrayList<String> trainingsName;

    {
        trainingsName = new ArrayList<>();
    }

    private MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStudentClassPageBinding.inflate(inflater, container, false);

        studentUsername = StudentClassPageFragmentArgs.fromBundle(getArguments()).getStudentUsername();
        professorUsername = StudentClassPageFragmentArgs.fromBundle(getArguments()).getProfessorUsername();
        className = StudentClassPageFragmentArgs.fromBundle(getArguments()).getClassName();

        ((MainActivity) getActivity()).setActionBarTitle(
                "Class " + className + " (" + professorUsername + ")");

        thisClass = Class.getClassByClassName(professorUsername, className);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Training> trainings = thisClass.getTrainings();

        trainingsName.clear();
        for (Training training: trainings) {
            trainingsName.add(training.getName());
        }

        RecyclerView recyclerView = view.findViewById(R.id.class_trainings_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), trainingsName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        NavHostFragment.findNavController(StudentClassPageFragment.this)
                .navigate(StudentClassPageFragmentDirections.
                        actionStudentClassPageFragmentToStudentTrainingPageFragment(studentUsername,
                                professorUsername, className, trainingsName.get(position)));
    }
}