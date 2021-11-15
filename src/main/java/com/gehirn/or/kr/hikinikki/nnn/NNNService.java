package com.gehirn.or.kr.hikinikki.nnn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NNNService {
    private final Map<String, WebSocketSession> conn = new HashMap<>();

    void putSession(WebSocketSession session) {
        conn.put(session.getId(), session);
        log.info(session.getId() + "joined");
    }

    void removeConnection(String sessionId) {
        conn.remove(sessionId);
        log.info(sessionId + "removed");
    }

    public void broadcast(WebSocketMessage msg) {
        conn.forEach((s, session) -> {
            try {
                session.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("message propagated to " + conn.size() + "connections");
    }
}
