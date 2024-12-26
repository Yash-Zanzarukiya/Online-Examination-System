import { ActionType, ExamEventType, MessageType, SubmissionType } from "./message-types";

export enum WebSocketStatus {
  CONNECTING = "CONNECTING",
  CONNECTED = "CONNECTED",
  DISCONNECTED = "DISCONNECTED",
  TERMINATED = "TERMINATED",
}

export type WebSocketMessage<T = any> = {
  type: MessageType;
  subtype: ExamEventType | ActionType | SubmissionType;
  payload: T;
};
