import { toastMessage } from "@/utils";
import { ActionType } from "../types/message-types";
import { WebSocketMessage } from "../types/types";
import { WebSocketClient } from "../WebSocketClient";
import { setExamQuestions, submitExam } from "@/features/ActiveExam/redux/activeExamSlice";
import { Action, Dispatch } from "@reduxjs/toolkit";
import { ActiveExamQuestionsResponse } from "@/features/ActiveExam/types";

// Main handler function
export const handleAction = (
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) => {
  const handler = eventHandlers[message.subtype as ActionType] || handleUnknownAction;
  handler(client, message, dispatch);
};

const eventHandlers: Record<
  string,
  (client: WebSocketClient, message: WebSocketMessage, dispatch: Dispatch<Action>) => void
> = {
  [ActionType.EXAM_QUESTION_RES]: handleExamQuestionRes,
  [ActionType.START_EXAM_RES]: handleStartExamRes,
  [ActionType.SUBMIT_EXAM_RES]: handleSubmitExamRes,
  [ActionType.PONG]: handlePong,
  [ActionType.FORCE_LOGOUT]: handleForceLogout,
  [ActionType.ERROR_PROCESSING_REQUEST]: handleErrorProcessingRequest,
};

function handleExamQuestionRes(
  _: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  const activeExamState: ActiveExamQuestionsResponse = message.payload;
  dispatch(setExamQuestions(activeExamState));
  toastMessage("Exam Question fetched");
}

function handleStartExamRes(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  toastMessage("Exam Started", message.payload);
}

function handleSubmitExamRes(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  toastMessage("Exam submitted successfully:");
  dispatch(submitExam());
}

function handlePong(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  toastMessage("Ping received, sending Pong", message.payload);
}

function handleForceLogout(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  toastMessage("Forced logout received");
}

function handleErrorProcessingRequest() {
  toastMessage("Error Processing Request of type: Action");
}

function handleUnknownAction(_: WebSocketClient, message: WebSocketMessage) {
  toastMessage("Unknown action subtype:", message.subtype);
}
