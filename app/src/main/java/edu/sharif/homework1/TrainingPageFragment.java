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

import edu.sharif.homework1.databinding.FragmentTrainingPageBinding;

public class TrainingPageFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    private FragmentTrainingPageBinding binding;
    private MyRecyclerViewAdapter adapter;

    private String professorUsername;
    private String className;
    private String trainingName;

    private Class ownerClass;
    private Training thisTraining;

    private ArrayList<String> answersText;

    {
        answersText = new ArrayList<>();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTrainingPageBinding.inflate(inflater, container, false);

        professorUsername = TrainingPageFragmentArgs.fromBundle(getArguments()).getProfessorUserName();
        className = TrainingPageFragmentArgs.fromBundle(getArguments()).getClassName();
        trainingName = TrainingPageFragmentArgs.fromBundle(getArguments()).getTrainingName();

        ownerClass = Class.getClassByName(className);
        thisTraining = Training.getTrainingByClassAndName(ownerClass, trainingName);

        ((MainActivity) getActivity()).setActionBarTitle("Training " + trainingName);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        answersText.clear();
        for (Answer answer: thisTraining.getAnswers()) {
            answersText.add(answer.getStudentUsername() + ": " + answer.getAnswerText());
        }

        RecyclerView recyclerView = view.findViewById(R.id.answers_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), answersText);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        binding.classNameText.setText("Professor: " + professorUsername + "\n"
                                      + "Class: " + className + "\n"
                                      + "Training: " + trainingName);

        binding.changeTrainingName.setOnClickListener(view1 -> {
            String newTrainingName = binding.newTrainingName.getText().toString();

            if (newTrainingName.isEmpty()) {
                Toast.makeText(getContext(),
                        "Please enter the new name.", Toast.LENGTH_LONG).show();
            }
            else {
                thisTraining.setName(newTrainingName);
                Toast.makeText(getContext(),
                        "Training name updated successfully.", Toast.LENGTH_LONG).show();
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
        String answer = answersText.get(position);
        String firstString = answer.split(" ")[0];
        String studentUsername = firstString.substring(0, firstString.length() - 1);

        NavHostFragment.findNavController(TrainingPageFragment.this)
                .navigate(TrainingPageFragmentDirections.
                        actionTrainingPageFragmentToTrainingGradingFragment(studentUsername,
                                professorUsername, className, trainingName));
    }
}