package com.example.myapplication;

public class Note {
    private String course;
    private String content;
    private String color;

    public Note(String course, String content, String color) {
        this.course = course;
        this.content = content;
        this.color = color;
    }

    public String getCourse() {
        return course;
    }

    public String getNote() {
        return content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setNote(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return course + "\n" + content;
    }
}
