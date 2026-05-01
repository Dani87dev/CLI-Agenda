package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NoteRepositoryImpl;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestNotesService {

    private NotesService notesService;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        notesService = new NotesService(new NoteRepositoryImpl());
    }

    @Test
    void testGetAllNotesEmpty() {
        List<Note> notes = notesService.getAllNotes();
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void testCreateNote() {
        Note note = notesService.createNote("New test note", 0);

        assertNotNull(note);
        assertEquals("New test note", note.getBody());

        List<Note> notes = notesService.getAllNotes();
        assertEquals(1, notes.size());
    }

    @Test
    void testDeleteNoteById() {
        Note note = notesService.createNote("Note to delete", 0);

        notesService.deleteNoteById(note.getId());

        assertNull(notesService.findNoteById(note.getId()));
    }

    @Test
    void testUpdateNote() {
        Note note = notesService.createNote("Original note", 0);

        note.changeBody("Updated note");
        notesService.updateNote(note);

        Note updated = notesService.findNoteById(note.getId());
        assertNotNull(updated);
        assertEquals("Updated note", updated.getBody());
    }

    @Test
    void testFindNoteById() {
        Note note = notesService.createNote("Note to find", 0);

        Note found = notesService.findNoteById(note.getId());

        assertNotNull(found);
        assertEquals("Note to find", found.getBody());
    }

    @Test
    void testFindNoteByIdNotFound() {
        Note note = notesService.findNoteById(999);
        assertNull(note);
    }

    @Test
    void testGetNotesByTaskId() {
        notesService.createNote("Note 1", 0);
        notesService.createNote("Note 2", 0);

        List<Note> notes = notesService.getNotesByTaskId(0);
        assertEquals(2, notes.size());
    }
}