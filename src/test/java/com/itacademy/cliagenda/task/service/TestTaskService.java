package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepositoryImpl;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskService {

    private TaskService taskService;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        taskService = new TaskService(new TaskRepositoryImpl());
    }

    @Test
    void testGetAllTasksEmpty() {
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testCreateTask() {
        Task task = taskService.createTask("New test task");

        assertNotNull(task);
        assertEquals("New test task", task.getBody());

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void testDeleteTaskById() {
        Task task = taskService.createTask("Task to delete");

        taskService.deleteTaskById(task.getId());

        assertNull(taskService.findTaskById(task.getId()));
    }

    @Test
    void testUpdateTask() {
        Task task = taskService.createTask("Original task");

        task.changeBody("Updated task");
        taskService.updateTask(task);

        Task updated = taskService.findTaskById(task.getId());
        assertNotNull(updated);
        assertEquals("Updated task", updated.getBody());
    }

    @Test
    void testFindTaskById() {
        Task task = taskService.createTask("Task to find");

        Task found = taskService.findTaskById(task.getId());

        assertNotNull(found);
        assertEquals("Task to find", found.getBody());
    }

    @Test
    void testFindTaskByIdNotFound() {
        Task task = taskService.findTaskById(999);
        assertNull(task);
    }

    @Test
    void testGetTasksByCompleted() {
        taskService.createTask("Incomplete task 1");
        taskService.createTask("Incomplete task 2");
        Task task3 = taskService.createTask("Completed task");
        task3.setCompleted(true);
        taskService.updateTask(task3);

        List<Task> incomplete = taskService.getTasksByCompleted(false);
        assertEquals(2, incomplete.size());

        List<Task> completed = taskService.getTasksByCompleted(true);
        assertEquals(1, completed.size());
    }

    @Test
    void testMarkTaskCompleted() {
        Task task = taskService.createTask("Uncompleted task");
        assertFalse(task.isCompleted());

        taskService.markTaskCompleted(task.getId(), true);

        Task updated = taskService.findTaskById(task.getId());
        assertTrue(updated.isCompleted());
    }

    @Test
    void testGetTasksByEventId() {
        taskService.createTask("Task without event");
        taskService.createTask("Task without event 2");

        List<Task> noEvent = taskService.getTasksByEventId(0);
        assertEquals(2, noEvent.size());
    }
}