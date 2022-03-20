package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Professor extends User {
    public static ArrayList<Professor> professors;

    static {
        professors = new ArrayList<>();
    }

    private String University;

    public Professor(String username, String password, String firstName, String lastName,
                     String university, FragmentActivity activity) {
        super(username, password, firstName, lastName);
        University = university;

        MainActivity.saveData(activity, this, "Professors" + professors.size());
        professors.add(this);
    }

    public String getUniversity() {
        return University;
    }
}
