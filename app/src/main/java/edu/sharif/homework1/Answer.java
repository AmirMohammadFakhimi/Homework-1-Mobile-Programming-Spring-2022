package edu.sharif.homework1;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class Answer {
    public static ArrayList<Answer> answers;

    static {
        answers = new ArrayList<>();
    }

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
        answers.add(this);
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isGradeSet() {
        return gradeSet;
    }

    public void setGradeSet(boolean gradeSet) {
        this.gradeSet = gradeSet;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
