package com.itacademy.cliagenda.event.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class TestEvent {

    private final LocalDateTime defaultDate = LocalDateTime.of(2025, 8, 15, 20, 0);

    @Test
    void testGetId() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        assertEquals(1, event.getId());
    }

    @Test
    void testGetTitle() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        assertEquals("Title", event.getTitle());
    }

    @Test
    void testGetDescription() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        assertEquals("Description", event.getDescription());
    }

    @Test
    void testGetDateTimeEvent() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        assertEquals(defaultDate, event.getDateTimeEvent());
    }

    @Test
    void testIsRecurringFalse() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        assertFalse(event.isRecurring());
    }

    @Test
    void testIsRecurringTrue() {
        Event event = new Event(1, "Title", "Description", defaultDate, true, false, 0);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeTitleValid() {
        Event event = new Event(1, "Original title", "Description", defaultDate, false, false, 0);
        event.changeTitle("New title");
        assertEquals("New title", event.getTitle());
    }

    @Test
    void testChangeTitleNull() {
        Event event = new Event(1, "Original title", "Description", defaultDate, false, false, 0);
        assertThrows(IllegalArgumentException.class, () -> event.changeTitle(null));
        assertEquals("Original title", event.getTitle());
    }

    @Test
    void testChangeTitleTooLong() {
        Event event = new Event(1, "Original title", "Description", defaultDate, false, false, 0);
        String longTitle = "a".repeat(100);
        assertThrows(IllegalArgumentException.class, () -> event.changeTitle(longTitle));
        assertEquals("Original title", event.getTitle());
    }

    @Test
    void testChangeDescriptionValid() {
        Event event = new Event(1, "Title", "Original description", defaultDate, false, false, 0);
        event.changeDescription("New description");
        assertEquals("New description", event.getDescription());
    }

    @Test
    void testChangeDescriptionNull() {
        Event event = new Event(1, "Title", "Original description", defaultDate, false, false, 0);
        event.changeDescription(null);
        assertNull(event.getDescription());
    }

    @Test
    void testChangeDescriptionTooLong() {
        Event event = new Event(1, "Title", "Original description", defaultDate, false, false, 0);
        String longDesc = "a".repeat(500);
        assertThrows(IllegalArgumentException.class, () -> event.changeDescription(longDesc));
        assertEquals("Original description", event.getDescription());
    }

    @Test
    void testSetRecurring() {
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        event.setRecurring(true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeDateEvent() {
        LocalDateTime newDate = LocalDateTime.of(2026, 1, 1, 12, 0);
        Event event = new Event(1, "Title", "Description", defaultDate, false, false, 0);
        event.changeDateEvent(newDate);
        assertEquals(newDate, event.getDateTimeEvent());
    }
}