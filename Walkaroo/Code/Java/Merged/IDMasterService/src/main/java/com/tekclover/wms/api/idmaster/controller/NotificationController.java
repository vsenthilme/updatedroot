package com.tekclover.wms.api.idmaster.controller;

import com.tekclover.wms.api.idmaster.model.websocketnotification.OutputMessage;
import com.tekclover.wms.api.idmaster.model.websocketnotification.WebSocketNotification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/notification")
    @SendTo("/topic/messages")
    public OutputMessage send(final WebSocketNotification message) throws Exception {

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time,null,null);
    }
}