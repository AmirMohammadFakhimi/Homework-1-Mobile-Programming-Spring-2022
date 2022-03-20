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
    private ArrayList<Student> students;

    {
        students = new ArrayList<>();
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public String getProfessorUsername() {
        return professorUsername;
    }
}
