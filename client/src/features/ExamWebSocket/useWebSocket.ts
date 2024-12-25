import { useContext } from "react";
import { WebSocketContext } from "./WebSocketProvider";

const useWebSocket = () => {
  const context = useContext(WebSocketContext);
  if (!context) throw new Error("useWebSocket must be used within a WebSocketProvider");
  return context.getClient();
};

export default useWebSocket;
