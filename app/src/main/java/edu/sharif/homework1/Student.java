package edu.sharif.homework1;

public class Student extends User {
    private String studentNumber;

    public Student(String username, String password, String firstName, String lastName,
                   String studentNumber) {
        super(username, password, firstName, lastName);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
}
