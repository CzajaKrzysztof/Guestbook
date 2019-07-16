package com.codecool.krk.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GuestbookServer {
    public void startServer() throws IOException {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/", new GuestbookHandler());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}
