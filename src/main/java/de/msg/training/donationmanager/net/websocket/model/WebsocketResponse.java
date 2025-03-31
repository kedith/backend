package de.msg.training.donationmanager.net.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WebsocketResponse {
    private String notificationType;
    private String usernameActor;
    private String usernameNotifier;
    private String notifierFirst;
    private String notifierLast;
    private String notifierMail;
    private String notifierMobile;
}
