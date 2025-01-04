import { toastMessage } from "@/utils";
import { SubmissionType } from "../types/message-types";
import { WebSocketMessage } from "../types/types";
import { WebSocketClient } from "../WebSocketClient";
import { Action, Dispatch } from "@reduxjs/toolkit";

const eventHandlers: Record<
  string,
  (client: WebSocketClient, message: WebSocketMessage, dispatch: Dispatch<Action>) => void
> = {
  [SubmissionType.MULTIPLE_CHOICE_SUBMISSION_RES]: handleMultipleChoiceSubmissionRes,
  [SubmissionType.PROGRAMMING_SUBMISSION_RES]: handleProgrammingSubmissionRes,
  [SubmissionType.ERROR_PROCESSING_REQUEST]: handleErrorProcessingRequest,
};

export const handleSubmission = (
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) => {
  const handler = eventHandlers[message.subtype as SubmissionType] || handleUnknownSubmissionType;
  handler(client, message, dispatch);
};

function handleMultipleChoiceSubmissionRes(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("MCQ Answer Saved");
}

function handleProgrammingSubmissionRes(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("Programming question saved");
}

function handleErrorProcessingRequest(
  _client: WebSocketClient,
  message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("Error Processing Request of type: Submission", message.payload);
}

function handleUnknownSubmissionType(message: WebSocketMessage) {
  toastMessage("Unknown submission subtype:", message.subtype);
}
