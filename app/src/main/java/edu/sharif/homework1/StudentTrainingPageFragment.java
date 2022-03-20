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

import edu.sharif.homework1.databinding.FragmentStudentClassPageBinding;
import edu.sharif.homework1.databinding.FragmentStudentPanelBinding;
import edu.sharif.homework1.databinding.FragmentStudentTrainingPageBinding;
import edu.sharif.homework1.databinding.FragmentTrainingPageBinding;

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

        ownerClass = Class.getClassByClassName(professorUsername, className);
        thisTraining = Training.getTrainingByClassAndName(ownerClass, trainingName);
        student = (Student) User.getUserByUsername(studentUsername);
        studentAnswer = thisTraining.getAnswerByStudentUsername(studentUsername);

        currentAnswer = (studentAnswer == null? "-" : studentAnswer.getAnswerText());
        currentGrade = (studentAnswer == null || !studentAnswer.isGradeSet()? "-" :
                String.valueOf(studentAnswer.getGrade()));


        ((MainActivity) getActivity()).setActionBarTitle("Training " + trainingName);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trainingNameText.setText("Professor: " + professorUsername + "\n"
                                         + "Class: " + className + "\n"
                                         + "Training: " + trainingName + "\n"
                                         + "Current Answer: " + currentAnswer + "\n"
                                         + "Current Grade: " + currentGrade);

        binding.submitTrainingButton.setOnClickListener(view1 -> {
            String answerText = binding.answerText.getText().toString();

            if (answerText.isEmpty()) {
                Toast.makeText(getContext(),
                        "Please fill the answer field.", Toast.LENGTH_LONG).show();
            }
            else {
                addAnswerToTraining(studentUsername, answerText);
                Toast.makeText(getContext(), "Your answer has been submitted", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addAnswerToTraining (String studentUsername, String answerText) {
        for (int i = 0; i < thisTraining.getAnswers().size(); i++) {
            Answer answer = thisTraining.getAnswers().get(i);
            if (answer.getStudent().equals(student)) {
                answer.setAnswerText(answerText);
                answer.setGradeSet(false);
                answer.setGrade(0);
                thisTraining.getAnswers().set(i, answer);
                return;
            }
        }

        Answer newAnswer = new Answer(thisTraining, student, answerText);
        thisTraining.getAnswers().add(newAnswer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}