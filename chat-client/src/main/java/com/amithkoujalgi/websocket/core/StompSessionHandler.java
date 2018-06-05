package com.amithkoujalgi.websocket.core;

import com.amithkoujalgi.websocket.message.ClientMessage;
import com.amithkoujalgi.websocket.message.ServerMessage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class StompSessionHandler
        extends StompSessionHandlerAdapter {
    private String userId;

    public StompSessionHandler(String userId) {
        this.userId = userId;
    }

    private void printHeaders(StompHeaders headers) {
        System.err.println("Headers:");
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            System.err.print("  " + e.getKey() + ": ");
            boolean first = true;
            for (String v : e.getValue()) {
                if (!first) System.err.print(", ");
                System.err.print(v);
                first = false;
            }
            System.err.println();
        }
        System.err.println("");
    }

    private void sendJsonMessage(StompSession session) {
        ClientMessage msg = new ClientMessage(userId,
                "hello from spring");
        session.send("/app/chat/java", msg);
    }

    private void subscribeToTopic(String topic, StompSession session) {
        session.subscribe(topic, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ServerMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers,
                                    Object payload) {
                System.err.println("Received message: " + payload.toString());
            }
        });
    }

    @Override
    public void afterConnected(StompSession session,
                               StompHeaders connectedHeaders) {
        System.err.println("[Connected to the chat server]\n");
        printHeaders(connectedHeaders);

        subscribeToTopic("/topic/messages", session);
        sendJsonMessage(session);
    }
}
