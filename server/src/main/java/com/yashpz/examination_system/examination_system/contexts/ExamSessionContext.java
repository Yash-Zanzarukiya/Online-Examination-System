package com.yashpz.examination_system.examination_system.contexts;

import com.yashpz.examination_system.examination_system.model.ExamSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ExamSessionContext {

    private static final String EXAM_SESSION = "EXAM_SESSION";

    public static void setExamSession(ExamSession session) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(EXAM_SESSION, session, RequestAttributes.SCOPE_REQUEST);
        }
    }

    public static ExamSession getExamSession() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return (ExamSession) requestAttributes.getAttribute(EXAM_SESSION, RequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

    public static void clear() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.removeAttribute(EXAM_SESSION, RequestAttributes.SCOPE_REQUEST);
        }
    }
}
