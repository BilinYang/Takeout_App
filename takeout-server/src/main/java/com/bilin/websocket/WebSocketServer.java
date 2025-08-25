package com.bilin.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // Store session map
    private static Map<String, Session> sessionMap = new HashMap();

    // Called when a new connection is established
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("Client：" + sid + " connected");
        sessionMap.put(sid, session);
    }

    // Called when a message is received from a client
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("Message from client " + sid + ": " + message);
    }

    // Called when a connection is closed
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("Connection closed: " + sid);
        sessionMap.remove(sid);
    }

    // Broadcast a message to all connected clients
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // Send message from server to client
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
