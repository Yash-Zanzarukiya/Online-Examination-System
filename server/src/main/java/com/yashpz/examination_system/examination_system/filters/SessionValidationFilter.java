package com.yashpz.examination_system.examination_system.filters;

import com.yashpz.examination_system.examination_system.contexts.ExamSessionContext;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.ExamSession;
import com.yashpz.examination_system.examination_system.service.ExamSessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SessionValidationFilter extends OncePerRequestFilter {

    private final ExamSessionService examSessionService;

    public SessionValidationFilter(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    private static final List<String> FILTER_PATHS = List.of(
            "/exam-session/ping", "/exam-session/resume",
            "/exam-attempt/update", "/exam-attempt/submit",
            "/exam-responses/mcq", "/exam-responses/programming", "/exam-responses/time-spent"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return FILTER_PATHS.stream().noneMatch(requestPath::equals);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String visitorId = request.getHeader("visitor_id");
        String sessionToken = "";

        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("session_token")) {
                        sessionToken = cookie.getValue();
                    }
                }
            }

            if (sessionToken.isEmpty() || visitorId == null)
                throw new ApiError(HttpStatus.UNAUTHORIZED, "Session token or visitor-id missing.");

            ExamSession examSession = examSessionService.validateSessionToken(sessionToken, visitorId);

            ExamSessionContext.setExamSession(examSession);
        } catch (ApiError e) {
            response.setStatus(e.getStatusCode());
            response.getWriter().write(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
