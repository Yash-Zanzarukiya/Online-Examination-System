package com.yashpz.examination_system.examination_system.socket.dto;

public enum Action {
    EXAM_QUESTION_REQ,
    EXAM_QUESTION_RES,
    START_EXAM_REQ,
    START_EXAM_RES,
    SUBMIT_EXAM_REQ,
    SUBMIT_EXAM_RES,
    PING,
    PONG,
    FORCE_LOGOUT,
    UNKNOWN_ACTION,
    ERROR_PROCESSING_REQUEST
}
