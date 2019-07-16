package com.codecool.krk.server;

import com.codecool.krk.dao.Iguestbook;
import com.codecool.krk.dao.sql.ConnectionPool;
import com.codecool.krk.model.GuestbookEntry;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestbookHandler implements HttpHandler {
    private Iguestbook guestbookDAO;

    public GuestbookHandler(Iguestbook guestbookDAO) {
        this.guestbookDAO = guestbookDAO;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")){
            response = "<html><body>" +
                    getAllEntries() +
                    createHtmlForm() +
                    "</body></html>";
        }

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("Write data do database");
            Map inputs = parseFormData(formData);
            guestbookDAO.addEntryToGuestbook((String) inputs.get("name"), (String) inputs.get("message"));

            response = "<html><body>" +
                    getAllEntries() +
                    createHtmlForm() +
                    "</body><html>";
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String getAllEntries() {
        StringBuilder sb = new StringBuilder();
        List<GuestbookEntry> guestbookEntryList = guestbookDAO.getAllEntries();
        guestbookEntryList.forEach(entry -> sb.append(createSingleEntry(entry)));
        return sb.toString();
    }

    private String createSingleEntry(GuestbookEntry entry) {
        return String.format("<div id=\"entry\"><div id=\"message\">%s</div><div id=\"name\">" +
                        "%s</div><div id=\"date\">%s</div></div>"
                , entry.getMessage(), entry.getName(), entry.getDate());
    }

    private String createHtmlForm() {
        return "<form method=\"POST\">\n" +
                "  Name:<br>\n" +
                "  <input type=\"text\" name=\"name\" placeholder=\"Name\">\n" +
                "  <br>\n" +
                "  Message:<br>\n" +
                "  <textarea name=\"message\" placeholder=\"Message\"></textarea>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> ";
    }
}
