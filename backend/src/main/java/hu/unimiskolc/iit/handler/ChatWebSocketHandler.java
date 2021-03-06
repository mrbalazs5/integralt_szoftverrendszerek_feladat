package hu.unimiskolc.iit.handler;

import hu.unimiskolc.iit.handler.core.Room;
import hu.unimiskolc.iit.handler.core.ColorChange;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();
    private Room room;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(room == null) {
            room = new Room();
        }

        webSocketSessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objM = new ObjectMapper();

        String payload = message.getPayload();

        if(isColorChangeEvent(payload)) {
            ColorChange colorChange = objM.readValue(payload, ColorChange.class);

            room.setColor(colorChange.getChatColor());
        } else if(!payload.isEmpty()) {
            room.addMessage(message.getPayload());
        }

        // update sessions
        for(WebSocketSession webSocketSession : webSocketSessions) {
            String roomJSON = objM.writeValueAsString(room);

            TextMessage textMessage = new TextMessage(roomJSON);

            webSocketSession.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    private boolean isColorChangeEvent(String payload) {
        ObjectMapper objM = new ObjectMapper();

        try {
            ColorChange colorChange = objM.readValue(payload, ColorChange.class);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
