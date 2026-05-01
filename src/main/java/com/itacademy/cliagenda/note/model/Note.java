package com.itacademy.cliagenda.note.model;


public class Note {
    private final int id;
    private String body;
    private int task_fk;


    public Note(int id, String body, int task_fk) {
        this.id = id;
        changeBody(body);
        this.task_fk = task_fk;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body) {

        if (body != null && body.length() > 250) {
            throw new IllegalArgumentException("Note body must be shorter than 250 characters.");
        }
        this.body = body;

    }

    public int getTask_fk() {
        return task_fk;
    }

    public void setTask_fk(int task_fk) {
        if (task_fk < 0) {
            throw new IllegalArgumentException("Task ID cannot be negative.");
        }
        this.task_fk = task_fk;
    }
}
