import { WebSocketMessage } from "../types/types";
import { MessageType } from "../types/message-types";
import { handleAction } from "./actionMessageHandler";
import { handleSubmission } from "./submissionMesasgeHandler";
import { handleExamEvent } from "./examEventMessageHandler";
import { WebSocketClient } from "../WebSocketClient";
import { Action, Dispatch } from "@reduxjs/toolkit";

export function handleSocketMessage(
  client: WebSocketClient,
  message: WebSocketMessage,
  dispatch: Dispatch<Action>
) {
  console.log("Received message of type: ", message.subtype);

  switch (message.type) {
    case MessageType.ExamEvent:
      handleExamEvent(client, message, dispatch);
      break;
    case MessageType.ACTION:
      handleAction(client, message, dispatch);
      break;
    case MessageType.SUBMISSION:
      handleSubmission(client, message, dispatch);
      break;
    default:
      console.warn(`Unknown message type: ${message.type}`);
  }
}
