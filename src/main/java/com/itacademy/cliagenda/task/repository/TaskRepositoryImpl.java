package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.infrastructure.sql.connection.ConnectionManager;
import com.itacademy.cliagenda.task.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements ITaskRepository {

    private final ConnectionManager connectionManager;

    public TaskRepositoryImpl() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public void save(Task task) {
        String query = "INSERT INTO tasks (id, body, event_fk, completed) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, task.getId());
            pstmt.setString(2, task.getBody());
            if (task.getEvent_fk() == 0) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, task.getEvent_fk());
            }
            pstmt.setBoolean(4, task.isCompleted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks";
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("body"),
                        rs.getInt("event_fk"), rs.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks: " + e.getMessage(), e);
        }
        return tasks;
    }

    @Override
    public Task findById(int id) {
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Task(rs.getInt("id"), rs.getString("body"),
                        rs.getInt("event_fk"), rs.getBoolean("completed"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding task: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting task: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Task task) {
        String query = "UPDATE tasks SET body = ?, event_fk = ?, completed = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, task.getBody());
            if (task.getEvent_fk() == 0) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, task.getEvent_fk());
            }
            pstmt.setBoolean(3, task.isCompleted());
            pstmt.setInt(4, task.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> findByEventId(int eventId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE event_fk = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("body"),
                        rs.getInt("event_fk"), rs.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding tasks by event: " + e.getMessage(), e);
        }
        return tasks;
    }

    @Override
    public List<Task> findByCompleted(boolean completed) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE completed = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, completed);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("body"),
                        rs.getInt("event_fk"), rs.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding tasks by completed: " + e.getMessage(), e);
        }
        return tasks;
    }
}