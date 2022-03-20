package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Student extends User {
    private static ArrayList<Student> students;

    static {
        students = new ArrayList<>();
    }

    private String studentNumber;

    public Student(String username, String password, String firstName, String lastName,
                   String studentNumber, FragmentActivity activity) {
        super(username, password, firstName, lastName);
        this.studentNumber = studentNumber;

        MainActivity.saveData(activity, this, "Students" + students.size());
        students.add(this);
    }

    public String getStudentNumber() {
        return studentNumber;
    }
}
