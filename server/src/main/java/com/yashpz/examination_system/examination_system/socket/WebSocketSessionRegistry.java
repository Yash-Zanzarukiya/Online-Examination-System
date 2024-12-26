package com.yashpz.examination_system.examination_system.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

  private final ConcurrentHashMap<String, WebSocketSession> activeSessionsMap = new ConcurrentHashMap<>();

  public void addSession(String sessionToken, WebSocketSession session) {
      activeSessionsMap.put(sessionToken, session);
  }

  public WebSocketSession getSession(String sessionToken) {
    return activeSessionsMap.get(sessionToken);
  }

  public boolean hasActiveSession(String sessionToken) {
    return activeSessionsMap.containsKey(sessionToken);
  }

  public void closeAndRemoveSession(String sessionToken) {
    try (WebSocketSession session = activeSessionsMap.remove(sessionToken)) {
      if (session != null)
        session.close(CloseStatus.NORMAL);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void forceLogoutToPreviousSession(String sessionToken) {
    try (WebSocketSession session = activeSessionsMap.remove(sessionToken)) {
      if (session != null)
        session.close(CloseStatus.POLICY_VIOLATION);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}