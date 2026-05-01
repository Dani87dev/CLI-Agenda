package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.ITaskRepository;

import java.util.List;

public class TaskService {

    private final ITaskRepository repo;

    public TaskService(ITaskRepository repo) {
        this.repo = repo;
    }

    public Task createTask(String body) {
        int id = generateNextId();
        Task newTask = new Task(id, body);
        repo.save(newTask);
        return newTask;
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task findTaskById(int id) {
        return repo.findById(id);
    }

    public void deleteTaskById(int id) {
        repo.deleteById(id);
    }

    public void updateTask(Task task) {
        repo.update(task);
    }

    public List<Task> getTasksByCompleted(boolean completed) {
        return repo.findByCompleted(completed);
    }

    public List<Task> getTasksByEventId(int eventId) {
        return repo.findByEventId(eventId);
    }

    public void markTaskCompleted(int id, boolean completed) {
        Task task = repo.findById(id);
        if (task != null) {
            task.setCompleted(completed);
            repo.update(task);
        }
    }

    private int generateNextId() {
        List<Task> tasks = repo.findAll();
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }
}