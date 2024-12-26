import { toastMessage } from "@/utils";
import { ActionType, ExamEventType, MessageType } from "../types/message-types";
import { WebSocketMessage } from "../types/types";
import { WebSocketClient } from "../WebSocketClient";
import { Action, Dispatch } from "@reduxjs/toolkit";
import { ConnectionResponse, ScheduledExamStatus } from "@/features/ActiveExam/types";
import {
  saveConnectionResponse,
  setFetchingQuestions,
} from "@/features/ActiveExam/redux/activeExamSlice";

// Main handler function
export const handleExamEvent = (
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) => {
  const handler = eventHandlers[message.subtype as ExamEventType] || handleUnknownMessage;
  handler(client, message, dispatch);
};

const eventHandlers: Record<
  string,
  (client: WebSocketClient, message: WebSocketMessage, dispatch: Dispatch<Action>) => void
> = {
  [ExamEventType.CONNECT_RES]: handleConnectRes,
  [ExamEventType.TAB_SWITCH_RES]: handleTabSwitchRes,
  [ExamEventType.LOGOUT_RES]: handleLogoutRes,
  [ExamEventType.ERROR_PROCESSING_REQUEST]: handleErrorProcessingRequest,
};

function handleConnectRes(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  const connectionResponse: ConnectionResponse = message.payload;
  dispatch(saveConnectionResponse(connectionResponse));
  if (connectionResponse.activeExamData.status === ScheduledExamStatus.LIVE) {
    dispatch(setFetchingQuestions(true));
    client.send({
      type: MessageType.ACTION,
      subtype: ActionType.EXAM_QUESTION_REQ,
      payload: connectionResponse.sessionType,
    });
  }
}

function handleTabSwitchRes(
  _client: WebSocketClient,
  message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("TAB_SWITCH_RES", message.payload);
}

function handleLogoutRes(
  _client: WebSocketClient,
  message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("LOGOUT_RES", message.payload);
}

function handleErrorProcessingRequest(
  _client: WebSocketClient,
  _message: WebSocketMessage,
  _dispatch: Dispatch<Action>
) {
  toastMessage("Error Processing Request of type: ExamEvent");
}

function handleUnknownMessage(message: WebSocketMessage) {
  toastMessage("Unknown message", message.subtype);
}
