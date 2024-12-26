package com.yashpz.examination_system.examination_system.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public class SessionTokenHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    if (request instanceof ServletServerHttpRequest) {
      HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
      HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

      String sessionToken = getSessionTokenFromCookies(servletRequest);
      System.out.println("sessionToken: "+sessionToken);
      if (sessionToken == null) {
        servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session Token not found");
        return false;
      }
      attributes.put("sessionToken", sessionToken);
      return true;
    }
    return false;
  }
    @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
      // nothing
  }

  private String getSessionTokenFromCookies(HttpServletRequest request) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("session_token".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
