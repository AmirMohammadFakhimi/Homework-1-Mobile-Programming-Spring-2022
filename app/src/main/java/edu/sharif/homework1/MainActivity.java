package edu.sharif.homework1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import edu.sharif.homework1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);

//        uncomment to delete all saved data
//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
        retrieveData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    public static void saveData(FragmentActivity activity, Object object, String identifier) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(identifier, json);
        prefsEditor.apply();
    }

    private void retrieveData() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("Professors0", "");
        int i = 1;
        for (i = 1; !json.isEmpty(); i++) {
            Professor professor = gson.fromJson(json, Professor.class);
            new Professor(professor.getUsername(), professor.getPassword(), professor.getFirstName(),
                    professor.getLastName(), professor.getUniversity(), this);

            json = sharedPreferences.getString("Professors" + i, "");
        }

        json = sharedPreferences.getString("Students0", "");
        for (i = 1; !json.isEmpty(); i++) {
            Student student = gson.fromJson(json, Student.class);
            new Student(student.getUsername(), student.getPassword(), student.getFirstName(),
                    student.getLastName(), student.getStudentNumber(), this);

            json = sharedPreferences.getString("Students" + i, "");
        }

        json = sharedPreferences.getString("Classes0", "");
        for (i = 1; !json.isEmpty(); i++) {
            Class c1 = gson.fromJson(json, Class.class);

            Professor professor = (Professor) User.getUserByUsername(c1.getProfessorUsername());
            Class c2 = new Class(c1.getName(), professor.getUsername(), this);
            professor.addClass(c2);

            ArrayList<Student> students = c1.getStudents();
            for (Student s : students) {
                Student newStudent = (Student) User.getUserByUsername(s.getUsername());
                c2.addStudent(newStudent);
                newStudent.addClass(c2);
            }

            json = sharedPreferences.getString("Classes" + i, "");
        }
    }
}