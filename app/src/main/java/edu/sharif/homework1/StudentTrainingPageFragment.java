package edu.sharif.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.sharif.homework1.databinding.FragmentStudentTrainingPageBinding;

public class StudentTrainingPageFragment extends Fragment {

    private FragmentStudentTrainingPageBinding binding;
    private MyRecyclerViewAdapter adapter;

    private String studentUsername;
    private String professorUsername;
    private String className;
    private String trainingName;

    private Class ownerClass;
    private Training thisTraining;
    private Student student;
    private Answer studentAnswer;
    private String currentAnswer;
    private String currentGrade;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentStudentTrainingPageBinding.inflate(inflater, container, false);

        studentUsername = StudentTrainingPageFragmentArgs.fromBundle(getArguments()).getStudentUsername();
        professorUsername = StudentTrainingPageFragmentArgs.fromBundle(getArguments()).getProfessorUsername();
        className = StudentTrainingPageFragmentArgs.fromBundle(getArguments()).getClassName();
        trainingName = StudentTrainingPageFragmentArgs.fromBundle(getArguments()).getTrainingName();

        ownerClass = Class.getClassByName(className);
        thisTraining = Training.getTrainingByClassAndName(ownerClass, trainingName);
        student = (Student) User.getUserByUsername(studentUsername);
        studentAnswer = thisTraining.getAnswerByStudentUsername(studentUsername);

        currentAnswer = (studentAnswer == null ? "-" : studentAnswer.getAnswerText());
        currentGrade = (studentAnswer == null || !studentAnswer.isGradeSet() ? "-" :
                String.valueOf(studentAnswer.getGrade()));


        ((MainActivity) getActivity()).setActionBarTitle("Training " + trainingName);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trainingNameText.setText(getStudentGradingText());

        binding.submitTrainingButton.setOnClickListener(view1 -> {
            String answerText = binding.answerText.getText().toString();

            if (answerText.isEmpty()) {
                Toast.makeText(getContext(),
                        "Please fill the answer field.", Toast.LENGTH_LONG).show();
            } else {
                addAnswerToTraining(answerText);
                currentAnswer = answerText;
                Toast.makeText(getContext(), "Your answer has been submitted", Toast.LENGTH_LONG).show();
                binding.trainingNameText.setText(getStudentGradingText());
            }
        });
    }

    private void addAnswerToTraining(String answerText) {
        for (int i = 0; i < thisTraining.getAnswersId().size(); i++) {
            int answerId = thisTraining.getAnswersId().get(i);
            Answer answer = Answer.getAnswerById(answerId);

            Student newStudent = (Student) User.getUserByUsername(answer.getStudentUsername());

            if (newStudent.equals(student)) {
                answer.setAnswerText(answerText, getActivity());
                answer.setGradeSet(false, getActivity());
                answer.setGrade(0, getActivity());
                thisTraining.getAnswersId().set(i, answer.getId());
                MainActivity.saveData(getActivity(), thisTraining,
                        "Trainings" + thisTraining.getId());

                return;
            }
        }

        Answer newAnswer = new Answer(student.getUsername(), answerText, getActivity());
        thisTraining.getAnswersId().add(newAnswer.getId());
        MainActivity.saveData(getActivity(), thisTraining,
                "Trainings" + thisTraining.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getStudentGradingText() {
        return "Professor: " + professorUsername + "\n"
                + "Class: " + className + "\n"
                + "Training: " + trainingName + "\n"
                + "Current Answer: " + currentAnswer + "\n"
                + "Current Grade: " + currentGrade;
    }
}