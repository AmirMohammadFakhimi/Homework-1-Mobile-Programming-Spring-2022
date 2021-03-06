package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Answer {
    public static ArrayList<Answer> answers;

    static {
        answers = new ArrayList<>();
    }

    private int id;
    private String studentUsername;
    private String answerText;
    private boolean gradeSet;
    private int grade;

    public Answer(String studentUsername, String answerText, FragmentActivity activity) {
        this.studentUsername = studentUsername;
        this.answerText = answerText;
        this.gradeSet = false;
        this.grade = 0;

        MainActivity.saveData(activity, this, "Answers" + answers.size());
        id = answers.size();
        answers.add(this);
    }

    public static Answer getAnswerById(int id) {
        return answers.get(id);
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText, FragmentActivity activity) {
        this.answerText = answerText;

        MainActivity.saveData(activity, this,
                "Answers" + id);
    }

    public boolean isGradeSet() {
        return gradeSet;
    }

    public void setGradeSet(boolean gradeSet, FragmentActivity activity) {
        this.gradeSet = gradeSet;

        MainActivity.saveData(activity, this,
                "Answers" + id);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade, FragmentActivity activity) {
        this.grade = grade;

        MainActivity.saveData(activity, this,
                "Answers" + id);
    }

    public int getId() {
        return id;
    }
}
