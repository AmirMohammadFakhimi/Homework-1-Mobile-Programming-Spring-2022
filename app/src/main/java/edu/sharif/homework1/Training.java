package edu.sharif.homework1;

import java.util.ArrayList;

public class Training {
    private String name;
    private Class ownerClass;

    private ArrayList<Answer> answers;

    {
        answers = new ArrayList<>();
    }

    public Training (String name, Class ownerClass) {
        this.name = name;
        this.ownerClass = ownerClass;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) { this.name = name; }

    public ArrayList<Answer> getAnswers() { return answers; }

    public static Training getTrainingByClassAndName(Class ownerClass, String trainingName) {
        for (Training training: ownerClass.getTrainings()) {
            if (training.getName().equals(trainingName)) {
                return training;
            }
        }
        return null;
    }

    public Answer getAnswerByStudentUsername(String studentUsername) {
        for (Answer answer: answers) {
            if (answer.getStudent().getUsername().equals(studentUsername)) {
                return answer;
            }
        }
        return null;
    }
}
