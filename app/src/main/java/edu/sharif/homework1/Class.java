package edu.sharif.homework1;

import java.util.ArrayList;

public class Class {
    public static ArrayList<Class> classes;

    static {
        classes = new ArrayList<>();
    }

    private String name;
    private Professor professor;
    private ArrayList<Student> students;
    private ArrayList<Training> trainings;

    {
        students = new ArrayList<>();
        trainings = new ArrayList<>();
    }

    public Class(String name, Professor professor) {
        this.name = name;
        this.professor = professor;

        classes.add(this);
    }

    public static Class getClassByClassName(String professorUsername, String className) {
        for (Class c : classes) {
            if (c.getName().equals(className) && c.getProfessor().getUsername().equals(professorUsername)) {
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
    public Professor getProfessor() { return professor; }
}
