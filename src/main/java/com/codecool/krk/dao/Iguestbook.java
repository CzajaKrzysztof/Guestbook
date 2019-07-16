package com.codecool.krk.dao;

import com.codecool.krk.model.GuestbookEntry;

import java.util.List;

public interface Iguestbook {

    void addEntryToGuestbook(String name, String message);
    List<GuestbookEntry> getAllEntries();
}
