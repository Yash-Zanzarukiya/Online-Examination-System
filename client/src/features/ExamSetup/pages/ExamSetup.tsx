import { useWebSocket } from "@/hooks";
import ExamSetUpLoading from "../components/ExamSetUpLoading";
import { useEffect } from "react";
import { WebSocketMessage } from "@/features/ExamWebSocket/types/types";
import { handleSocketMessage } from "@/features/ExamWebSocket/handlers/socketMessageHandler";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { navigateTo } from "@/utils";
import { useParams } from "react-router-dom";
import { ScheduledExamStatus } from "@/features/ActiveExam/types";
import ExamLobby from "./ExamLobby";
import { setNewLoginDetected } from "@/features/ActiveExam/redux/activeExamSlice";

export default function ExamSetup() {
  const webSocketClient = useWebSocket();
  const dispatch = useAppDispatch();
  const { examId } = useParams();
  const { activeExamData } = useAppSelector(({ activeExam }) => activeExam);

  useEffect(() => {
    const socket = webSocketClient.getSocket();
    socket.onmessage = (event) => {
      const message: WebSocketMessage = JSON.parse(event.data);
      handleSocketMessage(webSocketClient, message, dispatch);
    };
    socket.onclose = (closeEvent) => {
      if (closeEvent.code === 1008) dispatch(setNewLoginDetected(true));
    };
  }, [webSocketClient]);

  if (activeExamData?.status == ScheduledExamStatus.SCHEDULED) return <ExamLobby />;

  if (activeExamData?.status == ScheduledExamStatus.LIVE) {
    navigateTo("/active-exam/" + examId);
  }

  return <ExamSetUpLoading />;
}
