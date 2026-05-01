package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.IEventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    private final IEventRepository repo;

    public EventService(IEventRepository repo) {
        this.repo = repo;
    }

    public Event createEvent(String title, String description,
                             LocalDateTime dateTime, boolean recurring,
                             boolean annualRecurring, int recurrenceInterval) {
        int id = generateNextId();
        Event newEvent = new Event(id, title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        repo.save(newEvent);
        return newEvent;
    }

    public List<Event> getAllEvents() {
        return repo.findAll();
    }

    public Event findEventById(int id) {
        return repo.findById(id);
    }

    public void deleteEventById(int id) {
        repo.deleteById(id);
    }

    public void updateEvent(Event event) {
        repo.update(event);
    }

    public List<LocalDateTime> getNextRecurrencies(Event event) {
        List<LocalDateTime> dates = new ArrayList<>();
        if (!event.isRecurring()) return dates;

        int months = event.isAnnualRecurring() ? 12 : event.getRecurrenceInterval();
        LocalDateTime next = event.getDateTimeEvent();

        for (int i = 0; i < 5; i++) {
            next = next.plusMonths(months);
            dates.add(next);
        }
        return dates;
    }

    private int generateNextId() {
        List<Event> events = repo.findAll();
        int maxId = 0;
        for (Event event : events) {
            if (event.getId() > maxId) {
                maxId = event.getId();
            }
        }
        return maxId + 1;
    }
}