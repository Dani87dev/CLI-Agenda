package com.itacademy.cliagenda.task.model;

public class Task {
    private final int id;
    private String body;
    private int event_fk;
    private boolean completed;

    public Task(int id, String body) {
        this.id = id;
        changeBody(body);
        this.event_fk = 0;
        this.completed = false;
    }

    public Task(int id, String body, int event_fk) {
        this.id = id;
        changeBody(body);
        this.event_fk = event_fk;
        this.completed = false;
    }

    public Task(int id, String body, int event_fk, boolean completed) {
        this.id = id;
        changeBody(body);
        this.event_fk = event_fk;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body) {
        if (body != null && body.length() > 250) {
            throw new IllegalArgumentException("Task body must be shorter than 250 characters.");
        }
        this.body = body;
    }

    public int getEvent_fk() {
        return event_fk;
    }

    public void setEvent_fk(int event_fk) {
        if (event_fk < 0) {
            throw new IllegalArgumentException("Event ID cannot be negative.");
        }
        this.event_fk = event_fk;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}