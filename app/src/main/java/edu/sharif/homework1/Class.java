package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Class {
    public static ArrayList<Class> classes;

    static {
        classes = new ArrayList<>();
    }

    private String name;
    private String professorUsername;
    private ArrayList<String> studentsUsername;
    private ArrayList<Integer> trainingsId;

    {
        studentsUsername = new ArrayList<>();
        trainingsId = new ArrayList<>();
    }

    public Class(String name, String professorUsername, FragmentActivity activity) {
        this.name = name;
        this.professorUsername = professorUsername;

        MainActivity.saveData(activity, this, "Classes" + classes.size());
        classes.add(this);
    }

    public static Class getClassByName(String name) {
        for (Class c : classes) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<String> getStudentsUsername() {
        return studentsUsername;
    }

    public ArrayList<Integer> getTrainingsId() {
        return trainingsId;
    }

    public String getName() {
        return name;
    }

    public void addStudent(String studentUsername, FragmentActivity activity) {
        studentsUsername.add(studentUsername);
        MainActivity.saveData(activity, this, "Classes" + classes.indexOf(this));
    }

    public void addTraining(int trainingId, FragmentActivity activity) {
        trainingsId.add(trainingId);
        MainActivity.saveData(activity, this, "Classes" + classes.indexOf(this));
    }

    public void setTrainingsId(ArrayList<Integer> trainingsId, FragmentActivity activity) {
        this.trainingsId = trainingsId;
        MainActivity.saveData(activity, this, "Classes" + classes.indexOf(this));
    }

    public String getProfessorUsername() { return professorUsername; }
}
