package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.infrastructure.sql.connection.ConnectionManager;
import com.itacademy.cliagenda.note.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements INoteRepository {

    private final ConnectionManager connectionManager;

    public NoteRepositoryImpl() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public void save(Note note) {
        String query = "INSERT INTO notes (id, body, task_fk) VALUES (?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, note.getId());
            pstmt.setString(2, note.getBody());
            pstmt.setInt(3, note.getTask_fk());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving note: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, task_fk FROM notes";
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                notes.add(new Note(rs.getInt("id"), rs.getString("body"), rs.getInt("task_fk")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving notes: " + e.getMessage(), e);
        }
        return notes;
    }

    @Override
    public Note findById(int id) {
        String query = "SELECT id, body, task_fk FROM notes WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Note(rs.getInt("id"), rs.getString("body"), rs.getInt("task_fk"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding note: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting note: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Note note) {
        String query = "UPDATE notes SET body = ?, task_fk = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, note.getBody());
            pstmt.setInt(2, note.getTask_fk());
            pstmt.setInt(3, note.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating note: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Note> findByTaskId(int taskId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, task_fk FROM notes WHERE task_fk = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notes.add(new Note(rs.getInt("id"), rs.getString("body"), rs.getInt("task_fk")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding notes by task: " + e.getMessage(), e);
        }
        return notes;
    }
}