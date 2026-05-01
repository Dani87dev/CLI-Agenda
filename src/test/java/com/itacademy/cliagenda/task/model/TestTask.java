package com.itacademy.cliagenda.task.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestTask {

    @Test
    void testGetId() {
        Task task = new Task(1, "Test body", 1);
        assertEquals(1, task.getId());
    }

    @Test
    void testGetBody() {
        Task task = new Task(1, "Test body", 1);
        assertEquals("Test body", task.getBody());
    }

    @Test
    void testChangeBodyValid() {
        Task task = new Task(1, "Original body", 1);
        task.changeBody("Updated body");
        assertEquals("Updated body", task.getBody());
    }

    @Test
    void testChangeBodyExceedsLength() {
        Task task = new Task(1, "Original body", 1);
        String longBody = "a".repeat(251);
        assertThrows(IllegalArgumentException.class, () -> task.changeBody(longBody));
        assertEquals("Original body", task.getBody());
    }

    @Test
    void testConstructorValidBody() {
        Task task = new Task(1, "Valid body", 1);
        assertEquals("Valid body", task.getBody());
    }

    @Test
    void testConstructorNullBody() {
        Task task = new Task(1, null, 0);
        assertNull(task.getBody());
    }

    @Test
    void testChangeBodyNull() {
        Task task = new Task(1, "Original body", 1);
        task.changeBody(null);
        assertNull(task.getBody());
    }

    @Test
    void testGetEventFk() {
        Task task = new Task(1, "Test body", 5);
        assertEquals(5, task.getEvent_fk());
    }

    @Test
    void testGetEventFkDefault() {
        Task task = new Task(1, "Test body");
        assertEquals(0, task.getEvent_fk());
    }

    @Test
    void testSetEventFkValid() {
        Task task = new Task(1, "Test body", 0);
        task.setEvent_fk(5);
        assertEquals(5, task.getEvent_fk());
    }

    @Test
    void testSetEventFkNegative() {
        Task task = new Task(1, "Test body", 5);
        assertThrows(IllegalArgumentException.class, () -> task.setEvent_fk(-1));
        assertEquals(5, task.getEvent_fk());
    }

    @Test
    void testSetEventFkZero() {
        Task task = new Task(1, "Test body", 0);
        task.setEvent_fk(0);
        assertEquals(0, task.getEvent_fk());
    }

    @Test
    void testConstructorTwoParams() {
        Task task = new Task(1, "Simple task");
        assertEquals(1, task.getId());
        assertEquals("Simple task", task.getBody());
        assertEquals(0, task.getEvent_fk());
        assertFalse(task.isCompleted());
    }

    @Test
    void testSetCompleted() {
        Task task = new Task(1, "Task", 0);
        assertFalse(task.isCompleted());
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }
}