package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.homework1.databinding.FragmentClassPageBinding;

public class ClassPageFragment extends Fragment  implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentClassPageBinding binding;

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

        binding = FragmentClassPageBinding.inflate(inflater, container, false);

        professorUsername = ClassPageFragmentArgs.fromBundle(getArguments()).getProfessorName();
        className = ClassPageFragmentArgs.fromBundle(getArguments()).getClassName();

        ((MainActivity) getActivity()).setActionBarTitle(
                "Class " + className + " (" + professorUsername + ")");

        thisClass = Class.getClassByName(className);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Integer> trainings = thisClass.getTrainingsId();

        trainingsName.clear();
        for (int trainingId : trainings) {
            trainingsName.add(Training.getTrainingById(trainingId).getName());
        }

        RecyclerView recyclerView = view.findViewById(R.id.class_training_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), trainingsName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        binding.createTrainingButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(ClassPageFragment.this)
                    .navigate(ClassPageFragmentDirections
                                    .actionClassPageFragmentToCreateTrainingFragment(
                                            professorUsername, className));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onItemClick(View view, int position) {
        NavHostFragment.findNavController(ClassPageFragment.this)
                .navigate(ClassPageFragmentDirections.
                        actionClassPageFragmentToTrainingPageFragment(professorUsername,
                                className, trainingsName.get(position)));
    }
}