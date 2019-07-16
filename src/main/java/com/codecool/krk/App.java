package com.codecool.krk;

import com.codecool.krk.controller.Controller;
import com.codecool.krk.dao.Iguestbook;
import com.codecool.krk.dao.sql.ConnectionPool;
import com.codecool.krk.dao.sql.Guestbook;
import com.codecool.krk.dao.sql.IConnectionPool;

import java.sql.SQLException;

public class App {
    public static void main( String[] args )
    {
        final String URL = "jdbc:postgresql://127.0.0.1:5432/Guestbook";
        final String USER = "guestbook";
        final String PASSWORD = "guestbook";

        IConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.create(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Iguestbook guestbookDAO = new Guestbook(connectionPool);

        Controller controller = new Controller();
        controller.run();
    }
}
