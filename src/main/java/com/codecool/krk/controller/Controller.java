package com.codecool.krk.controller;

import com.codecool.krk.server.GuestbookServer;

import java.io.IOException;

public class Controller {
    private GuestbookServer server;

    public Controller(GuestbookServer server) {
        this.server = server;
    }

    public void run() {
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
