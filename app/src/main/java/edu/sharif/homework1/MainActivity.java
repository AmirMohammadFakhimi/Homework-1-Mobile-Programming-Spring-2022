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
        String json = sharedPreferences.getString("Classes0", "");

        retrieveProfessors(sharedPreferences, gson);
        retrieveStudents(sharedPreferences, gson);
        retrieveAnswers(sharedPreferences, gson);
        retrieveTrainings(sharedPreferences, gson);
        retrieveClasses(sharedPreferences, gson);

    }

    private void retrieveProfessors(SharedPreferences sharedPreferences, Gson gson) {
        String json = sharedPreferences.getString("Professors0", "");
        for (int i = 1; !json.isEmpty(); i++) {
            Professor professor = gson.fromJson(json, Professor.class);
            new Professor(professor.getUsername(), professor.getPassword(), professor.getFirstName(),
                    professor.getLastName(), professor.getUniversity(), this);

            json = sharedPreferences.getString("Professors" + i, "");
        }
    }

    private void retrieveStudents(SharedPreferences sharedPreferences, Gson gson) {
        String json = sharedPreferences.getString("Students0", "");
        for (int i = 1; !json.isEmpty(); i++) {
            Student student = gson.fromJson(json, Student.class);
            new Student(student.getUsername(), student.getPassword(), student.getFirstName(),
                    student.getLastName(), student.getStudentNumber(), this);

            json = sharedPreferences.getString("Students" + i, "");
        }
    }

    private void retrieveAnswers(SharedPreferences sharedPreferences, Gson gson) {
        String json = sharedPreferences.getString("Answers0", "");
        for (int i = 1; !json.isEmpty(); i++) {
            Answer answer = gson.fromJson(json, Answer.class);
            Answer newAnswer =
                    new Answer(answer.getStudentUsername(), answer.getAnswerText(), this);
            newAnswer.setGradeSet(answer.isGradeSet(), this);
            newAnswer.setGrade(answer.getGrade(), this);

            json = sharedPreferences.getString("Answers" + i, "");
        }
    }

    private void retrieveTrainings(SharedPreferences sharedPreferences, Gson gson) {
        String json = sharedPreferences.getString("Trainings0", "");
        for (int i = 1; !json.isEmpty(); i++) {
            Training training = gson.fromJson(json, Training.class);
            Training newTraining =
                    new Training(training.getName(), training.getOwnerClass(), this);

            newTraining.setAnswersId(training.getAnswersId(), this);

            json = sharedPreferences.getString("Trainings" + i, "");
        }
    }

    private void retrieveClasses(SharedPreferences sharedPreferences, Gson gson) {
        String json = sharedPreferences.getString("Classes0", "");
        for (int i = 1; !json.isEmpty(); i++) {
            Class c1 = gson.fromJson(json, Class.class);

            Professor professor = (Professor) User.getUserByUsername(c1.getProfessorUsername());
            Class c2 = new Class(c1.getName(), professor.getUsername(), this);
            professor.addClass(c2, this, true);

//            add students to class
            ArrayList<String> studentsUsername = c1.getStudentsUsername();
            for (String studentUsername : studentsUsername) {
                c2.addStudent(studentUsername, this);

                Student newStudent = (Student) User.getUserByUsername(studentUsername);
                newStudent.addClass(c2, this, false);
            }

//            add trainings to class
            c2.setTrainingsId(c1.getTrainingsId(), this);

            json = sharedPreferences.getString("Classes" + i, "");
        }
    }
}