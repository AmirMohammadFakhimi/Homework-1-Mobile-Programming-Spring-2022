package edu.sharif.homework1;

public class Professor extends User {
    private String University;

    public Professor(String username, String password, String firstName, String lastName,
                     String university) {
        super(username, password, firstName, lastName);
        University = university;
    }

    public String getUniversity() {
        return University;
    }
}
