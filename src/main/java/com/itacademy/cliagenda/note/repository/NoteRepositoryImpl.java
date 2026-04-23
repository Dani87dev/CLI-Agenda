package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

public class NoteRepositoryImpl implements INoteRepository{
    @Override
    public void save(Note note) {

    }

    @Override
    public List<Note> findAll() {
        return List.of();
    }

    @Override
    public Note findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(Note note) {

    }

    @Override
    public List<Note> findByTaskId(int taskId) {
        return List.of();
    }
}
