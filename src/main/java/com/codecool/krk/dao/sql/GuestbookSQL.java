package com.codecool.krk.dao.sql;

import com.codecool.krk.dao.Iguestbook;
import com.codecool.krk.model.GuestbookEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestbookSQL implements Iguestbook {
    private IConnectionPool connectionPool;

    public GuestbookSQL(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addEntryToGuestbook(String name, String message) {
        String query = "INSERT INTO guestbook_entries (name, message, date) " +
                "VALUES (?, ?, ?)";

        try {
            Connection connection = connectionPool.getConnection();
            insertQuestData(query, connection, name, message);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            System.err.println("SQLInsertException: " + e.getMessage());
        }
    }

    private void insertQuestData(String query, Connection connection, String name, String message) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, message);
            stmt.setDate(3, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();
        }
    }

    @Override
    public List<GuestbookEntry> getAllEntries() {
        List<GuestbookEntry> listOfEntries = new ArrayList<>();
        String query = "SELECT * FROM guestbook_entries ORDER BY id DESC";

        try {
            Connection connection = connectionPool.getConnection();
            prepareEntriesList(listOfEntries, query, connection);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            System.err.println("SQLSelectException: " + e.getMessage());
        }


        return listOfEntries;
    }

    private void prepareEntriesList(List<GuestbookEntry> listOfEntries, String query, Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            executeEntriesListQuery(listOfEntries, stmt);
        }
    }

    private void executeEntriesListQuery(List<GuestbookEntry> listOfEntries, PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                String message = rs.getString("message");
                Date date = rs.getDate("date");

                GuestbookEntry entry = new GuestbookEntry(name, message, date);

                listOfEntries.add(entry);
            }
        }
    }
}
