package hu.unimiskolc.iit.handler.core;

import org.springframework.web.socket.TextMessage;
import java.util.List;
import java.util.ArrayList;

public class Room {
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}