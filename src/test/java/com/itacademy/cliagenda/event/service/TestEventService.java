package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepositoryImpl;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestEventService {

    private EventService service;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        service = new EventService(new EventRepositoryImpl());
    }

    @Test
    void testGetAllEventsEmpty() {
        List<Event> events = service.getAllEvents();
        assertNotNull(events);
        assertTrue(events.isEmpty());
    }

    @Test
    void testCreateEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("New event", "Description", dateTime, false, false, 0);

        assertNotNull(event);
        assertEquals("New event", event.getTitle());

        List<Event> events = service.getAllEvents();
        assertEquals(1, events.size());
    }

    @Test
    void testDeleteEventById() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Event to delete", "Description", dateTime, false, false, 0);

        service.deleteEventById(event.getId());

        assertNull(service.findEventById(event.getId()));
    }

    @Test
    void testUpdateEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Original event", "Description", dateTime, false, false, 0);

        event.changeTitle("Updated event");
        service.updateEvent(event);

        Event updated = service.findEventById(event.getId());
        assertNotNull(updated);
        assertEquals("Updated event", updated.getTitle());
    }

    @Test
    void testFindEventById() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Event to find", "Description", dateTime, false, false, 0);

        Event found = service.findEventById(event.getId());

        assertNotNull(found);
        assertEquals("Event to find", found.getTitle());
    }

    @Test
    void testFindEventByIdNotFound() {
        Event event = service.findEventById(999);
        assertNull(event);
    }

    @Test
    void testGetNextRecurrenciesAnnual() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Annual event", "Description", dateTime, true, true, 0);

        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);

        assertEquals(5, recurrencies.size());
        assertEquals(2026, recurrencies.get(0).getYear());
    }

    @Test
    void testGetNextRecurrenciesMonthly() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Monthly event", "Description", dateTime, true, false, 3);

        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);

        assertEquals(5, recurrencies.size());
        assertEquals(9, recurrencies.get(0).getMonthValue());
    }

    @Test
    void testGetNextRecurrenciesNonRecurring() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Non recurring event", "Description", dateTime, false, false, 0);

        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);

        assertTrue(recurrencies.isEmpty());
    }
}