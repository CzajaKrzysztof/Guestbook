package com.codecool.krk.dao.sql;

import com.codecool.krk.dao.Iguestbook;
import com.codecool.krk.model.GuestbookEntry;

import java.util.List;

public class Guestbook implements Iguestbook {
    private IConnectionPool connectionPool;

    public Guestbook(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addEntryToGuestbook(String name, String message) {

    }

    @Override
    public List<GuestbookEntry> getAllEntries() {
        return null;
    }
}
