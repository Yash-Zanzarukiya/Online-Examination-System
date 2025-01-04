import { toastMessage } from "@/utils";
import { ActionType } from "../types/message-types";
import { WebSocketMessage } from "../types/types";
import { WebSocketClient } from "../WebSocketClient";
import {
  setExamQuestions,
  setNewLoginDetected,
  submitExam,
} from "@/features/ActiveExam/redux/activeExamSlice";
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
  _client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  const activeExamState: ActiveExamQuestionsResponse = message.payload;
  dispatch(setExamQuestions(activeExamState));
}

function handleStartExamRes(
  _client: WebSocketClient,
  message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("Exam Started", message.payload);
}

function handleSubmitExamRes(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  toastMessage("Exam submitted successfully:");
  dispatch(submitExam());
}

function handlePong(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {}

function handleForceLogout(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  dispatch(setNewLoginDetected(true));
  toastMessage("Forced logout received");
}

function handleErrorProcessingRequest() {
  toastMessage("Error Processing Request of type: Action");
}

function handleUnknownAction(_client: WebSocketClient, message: WebSocketMessage) {
  toastMessage("Unknown action subtype:", message.subtype);
}
