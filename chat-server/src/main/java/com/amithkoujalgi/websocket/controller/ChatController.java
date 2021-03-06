package com.amithkoujalgi.websocket.controller;

import com.amithkoujalgi.websocket.message.Message;
import com.amithkoujalgi.websocket.message.OutputMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("topic") String topic,
                              Message message) throws Exception {
        return new OutputMessage(message.getFrom(), message.getText(), topic);
    }
}
