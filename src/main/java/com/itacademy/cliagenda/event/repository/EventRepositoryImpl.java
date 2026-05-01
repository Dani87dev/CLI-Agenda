package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.infrastructure.sql.connection.ConnectionManager;
import com.itacademy.cliagenda.event.model.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements IEventRepository {

    private final ConnectionManager connectionManager;

    public EventRepositoryImpl() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    @Override
    public void save(Event event) {
        String query = "INSERT INTO events (id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, event.getId());
            pstmt.setString(2, event.getTitle());
            pstmt.setString(3, event.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(5, event.isRecurring());
            pstmt.setBoolean(6, event.isAnnualRecurring());
            pstmt.setInt(7, event.getRecurrenceInterval());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving event: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval FROM events";
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("eventDate").toLocalDateTime(),
                        rs.getBoolean("recurrent"),
                        rs.getBoolean("annualRecurring"),
                        rs.getInt("recurrenceInterval")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events: " + e.getMessage(), e);
        }
        return events;
    }

    @Override
    public Event findById(int id) {
        String query = "SELECT id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval FROM events WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("eventDate").toLocalDateTime(),
                        rs.getBoolean("recurrent"),
                        rs.getBoolean("annualRecurring"),
                        rs.getInt("recurrenceInterval")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding event: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting event: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Event event) {
        String query = "UPDATE events SET title = ?, description = ?, eventDate = ?, recurrent = ?, annualRecurring = ?, recurrenceInterval = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setTimestamp(3, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(4, event.isRecurring());
            pstmt.setBoolean(5, event.isAnnualRecurring());
            pstmt.setInt(6, event.getRecurrenceInterval());
            pstmt.setInt(7, event.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating event: " + e.getMessage(), e);
        }
    }
}