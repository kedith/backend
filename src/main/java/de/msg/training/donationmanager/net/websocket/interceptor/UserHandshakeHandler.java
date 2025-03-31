package de.msg.training.donationmanager.net.websocket.interceptor;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return new UserPrincipal("12345");
    }

}
