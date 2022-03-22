package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Training {
    public static ArrayList<Training> trainings;

    static {
        trainings = new ArrayList<>();
    }

    private int id;
    private String name;
    private String ownerClass;

    private ArrayList<Integer> answersId;

    {
        answersId = new ArrayList<>();
    }

    public Training (String name, String ownerClass, FragmentActivity activity) {
        this.name = name;
        this.ownerClass = ownerClass;

        id = trainings.size();
        MainActivity.saveData(activity, this, "Trainings" + id);
        trainings.add(this);
    }

    public static Training getTrainingByClassAndName(Class ownerClass, String trainingName) {
        for (int trainingId : ownerClass.getTrainingsId()) {
            Training training = getTrainingById(trainingId);

            if (training.getName().equals(trainingName)) {
                return training;
            }
        }
        return null;
    }

    public static Training getTrainingById(int id) {
        return trainings.get(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name, FragmentActivity activity) {
        this.name = name;
        MainActivity.saveData(activity, this, "Trainings" + id);
    }

    public void setAnswersId(ArrayList<Integer> answersId, FragmentActivity activity) {
        this.answersId = answersId;
        MainActivity.saveData(activity, this, "Trainings" + id);
    }

    public ArrayList<Integer> getAnswersId() {
        return answersId;
    }

    public String getOwnerClass() {
        return ownerClass;
    }

    public int getId() {
        return id;
    }

    public Answer getAnswerByStudentUsername(String studentUsername) {
        for (int answerId : answersId) {
            Answer answer = Answer.getAnswerById(answerId);
            if (answer.getStudentUsername().equals(studentUsername)) {
                return answer;
            }
        }
        return null;
    }
}
