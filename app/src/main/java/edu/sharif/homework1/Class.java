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

    {
        students = new ArrayList<>();
    }

    public Class(String name, Professor professor) {
        this.name = name;
        this.professor = professor;

        classes.add(this);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

//    TODO: can't create two classes with same name
}