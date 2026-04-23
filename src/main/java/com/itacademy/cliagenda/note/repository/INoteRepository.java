package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

public interface INoteRepository {

    void save(Note note);
    List<Note> findAll();
    Note findById(int id);
    void deleteById(int id);
    void update(Note note);
    List<Note> findByTaskId(int taskId);

}