package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;

import java.util.List;

public interface IEventRepository {
    void save(Event event);
    List<Event> findAll();
    Event findById(int id);
    void deleteById(int id);
    void update(Event event);
}