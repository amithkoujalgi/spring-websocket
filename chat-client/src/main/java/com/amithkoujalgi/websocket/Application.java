package com.amithkoujalgi.websocket;


import com.amithkoujalgi.websocket.core.SocketListener;

public class Application {
    public static void main(String args[]) throws Exception {
        String url = "ws://localhost:8080/chat";
        new SocketListener(url).start();
    }

}

