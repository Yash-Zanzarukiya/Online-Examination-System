import { toastMessage } from "@/utils";
import { WebSocketMessage } from "./types/types";

export class WebSocketClient {
  private socket: WebSocket;
  private isConnected = false;

  constructor(URL: string) {
    this.socket = new WebSocket(URL);

    this.socket.onopen = () => {
      this.isConnected = true;
    };

    this.socket.onclose = (event) => {
      this.isConnected = false;
      toastMessage(`WebSocket connection closed: ${event.reason} 😢`);
    };

    this.socket.onerror = (error) => {
      toastMessage("WebSocket error 😢");
      console.error("WebSocket error:", error);
    };
  }

  public send(message: WebSocketMessage) {
    if (this.isConnected) {
      console.log("sent message of type: " + message.subtype);
      console.log(message);
      console.log("----------------------------------------------");
      this.socket.send(JSON.stringify(message));
    } else {
      toastMessage("WebSocket is not connected. Cannot send message.");
    }
  }

  public getSocket() {
    return this.socket;
  }

  public close() {
    if (this.isConnected) {
      this.socket?.close();
      this.isConnected = false;
    }
  }
}
