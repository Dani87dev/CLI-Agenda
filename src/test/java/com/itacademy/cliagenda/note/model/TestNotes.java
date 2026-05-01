package com.itacademy.cliagenda.note.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestNotes {

    @Test
    void testGetId() {
        Note note = new Note(1, "Test body", 1);
        assertEquals(1, note.getId());
    }

    @Test
    void testGetBody() {
        Note note = new Note(1, "Test body", 1);
        assertEquals("Test body", note.getBody());
    }

    @Test
    void testChangeBodyValid() {
        Note note = new Note(1, "Original body", 1);
        note.changeBody("Updated body");
        assertEquals("Updated body", note.getBody());
    }

    @Test
    void testChangeBodyExceedsLength() {
        Note note = new Note(1, "Original body", 1);
        String longBody = "a".repeat(251);
        assertThrows(IllegalArgumentException.class, () -> note.changeBody(longBody));
        assertEquals("Original body", note.getBody());
    }

    @Test
    void testConstructorValidBody() {
        Note note = new Note(1, "Valid body", 1);
        assertEquals("Valid body", note.getBody());
    }

    @Test
    void testConstructorNullBody() {
        Note note = new Note(1, null, 0);
        assertNull(note.getBody());
    }

    @Test
    void testConstructorBodyExceedsLength() {
        String longBody = "a".repeat(251);
        assertThrows(IllegalArgumentException.class, () -> new Note(1, longBody, 1));
    }

    @Test
    void testChangeBodyNull() {
        Note note = new Note(1, "Original body", 1);
        note.changeBody(null);
        assertNull(note.getBody());
    }

    @Test
    void testGetTaskFk() {
        Note note = new Note(1, "Test body", 5);
        assertEquals(5, note.getTask_fk());
    }

    @Test
    void testGetTaskFkDefault() {
        Note note = new Note(1, "Test body", 0);
        assertEquals(0, note.getTask_fk());
    }

    @Test
    void testSetTaskFkValid() {
        Note note = new Note(1, "Test body", 0);
        note.setTask_fk(5);
        assertEquals(5, note.getTask_fk());
    }

    @Test
    void testSetTaskFkNegative() {
        Note note = new Note(1, "Test body", 5);
        assertThrows(IllegalArgumentException.class, () -> note.setTask_fk(-1));
        assertEquals(5, note.getTask_fk());
    }

    @Test
    void testSetTaskFkZero() {
        Note note = new Note(1, "Test body", 0);
        note.setTask_fk(0);
        assertEquals(0, note.getTask_fk());
    }
}