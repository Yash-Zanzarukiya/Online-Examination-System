import { createContext, useRef } from "react";
import { WebSocketClient } from "./WebSocketClient";

export const WebSocketContext = createContext<{
  getClient: () => WebSocketClient;
} | null>(null);

const socketURL = "ws://localhost:8080/ws/exam";

export function WebSocketProvider({ children }: { children: React.ReactNode }) {
  const clientRef = useRef<WebSocketClient | null>(null);

  const getClient = () => {
    if (!clientRef.current) {
      clientRef.current = new WebSocketClient(socketURL);
    }
    return clientRef.current;
  };

  return <WebSocketContext.Provider value={{ getClient }}>{children}</WebSocketContext.Provider>;
}
