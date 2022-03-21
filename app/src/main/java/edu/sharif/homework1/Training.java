package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Training {
    public static ArrayList<Training> trainings;

    static {
        trainings = new ArrayList<>();
    }

    private String name;
    private String ownerClass;

    private ArrayList<Answer> answers;

    {
        answers = new ArrayList<>();
    }

    public Training (String name, String ownerClass, FragmentActivity activity) {
        this.name = name;
        this.ownerClass = ownerClass;

        MainActivity.saveData(activity, this, "Trainings" + trainings.size());
        trainings.add(this);
    }

    public static Training getTrainingByClassAndName(Class ownerClass, String trainingName) {
        for (Training training: ownerClass.getTrainings()) {
            if (training.getName().equals(trainingName)) {
                return training;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) { this.name = name; }

    public ArrayList<Answer> getAnswers() { return answers; }

    public String getOwnerClass() {
        return ownerClass;
    }

    public Answer getAnswerByStudentUsername(String studentUsername) {
        for (Answer answer: answers) {
            if (answer.getStudentUsername().equals(studentUsername)) {
                return answer;
            }
        }
        return null;
    }
}
