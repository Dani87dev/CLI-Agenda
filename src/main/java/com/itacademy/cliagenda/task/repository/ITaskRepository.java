package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

public interface ITaskRepository {
    void save(Task task);
    List<Task> findAll();
    Task findById(int id);
    void deleteById(int id);
    void update(Task task);
    List<Task> findByEventId(int eventId);
    List<Task> findByCompleted(boolean completed);
}