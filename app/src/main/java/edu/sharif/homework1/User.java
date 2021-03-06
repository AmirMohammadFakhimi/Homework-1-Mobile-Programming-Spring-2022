package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> users;

    static {
        users = new ArrayList<>();
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private ArrayList<Class> classes;

    {
        classes = new ArrayList<>();
    }

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        users.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void addClass(Class c, FragmentActivity activity, boolean isProfessor) {
        if (isProfessor)
            MainActivity.saveData(activity, this, "Professors" + users.indexOf(this));
        else
            MainActivity.saveData(activity, this, "Students" + users.indexOf(this));

        classes.add(c);
    }
}
