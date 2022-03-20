package edu.sharif.homework1;

import java.util.ArrayList;

public class Class {
    public static ArrayList<Class> classes;

    static {
        classes = new ArrayList<>();
    }

    private String name;
    private String professorUsername;
    private ArrayList<Student> students;
    private ArrayList<Training> trainings;

    {
        students = new ArrayList<>();
        trainings = new ArrayList<>();
    }

    public Class(String name, String professorUsername) {
        this.name = name;
        this.professorUsername = professorUsername;

        classes.add(this);
    }

    public static Class getClassByClassName(String professorUsername, String className) {
        for (Class c : classes) {
            if (c.getName().equals(className) && c.getProfessorUsername().equals(professorUsername)) {
                return c;
            }
        }
        return null;
    }


    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }
    public String getProfessorUsername() { return professorUsername; }
}
