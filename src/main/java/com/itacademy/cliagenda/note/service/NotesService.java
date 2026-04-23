package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.INoteRepository;

import java.util.List;

public class NotesService {

    private final INoteRepository repo;

    public NotesService(INoteRepository repo) {
        this.repo = repo;
    }

    public Note createNote(String body, int taskId) {
        int id = generateNextId();
        Note newNote = new Note(id, body, taskId);
        repo.save(newNote);
        return newNote;
    }

    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    public Note findNoteById(int id) {
        return repo.findById(id);
    }

    public void deleteNoteById(int id) {
        repo.deleteById(id);
    }

    public void updateNote(Note note) {
        repo.update(note);
    }

    public List<Note> getNotesByTaskId(int taskId) {
        return repo.findByTaskId(taskId);
    }

    private int generateNextId() {
        List<Note> notes = repo.findAll();
        int maxId = 0;
        for (Note note : notes) {
            if (note.getId() > maxId) {
                maxId = note.getId();
            }
        }
        return maxId + 1;
    }
}