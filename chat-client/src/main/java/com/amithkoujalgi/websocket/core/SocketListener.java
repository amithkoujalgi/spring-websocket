package com.amithkoujalgi.websocket.core;

import com.amithkoujalgi.websocket.message.ClientMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SocketListener extends Thread {
    private String host;

    public SocketListener(String host) {
        this.host = host;
    }

    @Override
    public void run() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient =
                new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        String userId = "spring-client-" + UUID.randomUUID().toString();
        org.springframework.messaging.simp.stomp.StompSessionHandler sessionHandler = new StompSessionHandler(userId);
        StompSession session = null;
        try {
            session = stompClient.connect(host, sessionHandler)
                    .get();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                System.out.flush();
                String line = in.readLine();
                if (line == null) break;
                if (line.length() == 0) continue;
                ClientMessage msg = new ClientMessage(userId, line);
                session.send("/app/chat/java", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
