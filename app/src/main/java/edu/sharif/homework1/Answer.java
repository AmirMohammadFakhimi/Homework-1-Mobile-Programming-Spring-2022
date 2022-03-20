package edu.sharif.homework1;

import java.util.ArrayList;

public class Answer {
    private Training training;
    private Student student;
    private String answerText;
    private boolean gradeSet;
    private int grade;

    public Answer(Training training, Student student, String answerText) {
        this.training = training;
        this.student = student;
        this.answerText = answerText;
        this.gradeSet = false;
        this.grade = 0;
    }

    public Training getTraining() {
        return training;
    }

    public Student getStudent() {
        return student;
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
