package de.msg.training.donationmanager.net.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {
    public static final String THE_USERNAME = "The user with the username: ";
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessagesForUserDeleted(String username) {
        simpMessagingTemplate.convertAndSend( "/topic/userDeleted", THE_USERNAME + username + " has been manually deactivated.");
    }

    public void sendMessagesForUserDeactivated(String username) {
        simpMessagingTemplate.convertAndSend( "/topic/userDeactivated", THE_USERNAME + username + " has been deactivated due to 5 failed login attempts.");
    }

    public void sendMessagesForUserUpdated(String username) {
        simpMessagingTemplate.convertAndSend( "/topic/userUpdated", THE_USERNAME + username + " has been updated.");
    }
}
