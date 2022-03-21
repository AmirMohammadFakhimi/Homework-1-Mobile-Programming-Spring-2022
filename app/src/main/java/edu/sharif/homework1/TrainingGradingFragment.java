package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sharif.homework1.databinding.FragmentTrainingGradingBinding;

public class TrainingGradingFragment extends Fragment {

    private FragmentTrainingGradingBinding binding;

    private String studentUsername;
    private String professorUsername;
    private String className;
    private String trainingName;

    private Class ownerClass;
    private Training thisTraining;
    private Answer thisAnswer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTrainingGradingBinding.inflate(inflater, container, false);

        studentUsername = TrainingGradingFragmentArgs.fromBundle(getArguments()).getStudentUsername();
        professorUsername = TrainingGradingFragmentArgs.fromBundle(getArguments()).getProfessorUsername();
        className = TrainingGradingFragmentArgs.fromBundle(getArguments()).getClassName();
        trainingName = TrainingGradingFragmentArgs.fromBundle(getArguments()).getTrainingName();

        ownerClass = Class.getClassByName(className);
        thisTraining = Training.getTrainingByClassAndName(ownerClass, trainingName);
        thisAnswer = thisTraining.getAnswerByStudentUsername(studentUsername);

        ((MainActivity) getActivity()).setActionBarTitle("Set Grade for " + studentUsername);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.gradingTitle.setText("Student: " + studentUsername + "\n"
                + "Class: " + className + "\n"
                + "Training: " + trainingName + "\n"
                + "Current Grade: " + (thisAnswer.isGradeSet()? thisAnswer.getGrade() : "-"));

        binding.gradingSubmit.setOnClickListener(view1 -> {
            String gradeString = binding.Grade.getText().toString();

            if (gradeString.isEmpty()) {
                Toast.makeText(getContext(),
                        "Please enter the grade.", Toast.LENGTH_LONG).show();
            }
            else {
                int gradeNumber = Integer.parseInt(gradeString);
                if (gradeNumber < 0 || gradeNumber > 100) {
                    Toast.makeText(getContext(),
                            "Grade should be between 0 and 100.", Toast.LENGTH_LONG).show();
                }
                else {
                    thisAnswer.setGrade(gradeNumber);
                    thisAnswer.setGradeSet(true);
                    Toast.makeText(getContext(),
                            "Grade Updated successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}