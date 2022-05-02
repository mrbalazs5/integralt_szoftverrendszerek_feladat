package hu.unimiskolc.iit.handler.core;

import org.springframework.web.socket.TextMessage;

public class ColorChange {
    private String chatColor;

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public String getChatColor() {
        return this.chatColor;
    }
}