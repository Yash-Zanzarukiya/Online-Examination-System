import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { setFetchingQuestions } from "@/features/ActiveExam/redux/activeExamSlice";
import { SessionType } from "@/features/ActiveExam/types";
import { ActionType, MessageType } from "@/features/ExamWebSocket/types/message-types";
import { useWebSocket } from "@/hooks";
import { navigateTo } from "@/utils";
import { useCallback } from "react";
import { Button } from "react-day-picker";
import { useParams } from "react-router-dom";

function ExamLobby() {
  const dispatch = useAppDispatch();
  const { examId } = useParams();
  const client = useWebSocket();
  const activeExamData = useAppSelector(({ activeExam }) => activeExam.activeExamData);

  const onStart = useCallback(() => {
    dispatch(setFetchingQuestions(true));
    client.send({
      type: MessageType.ACTION,
      subtype: ActionType.EXAM_QUESTION_REQ,
      payload: SessionType.NORMAL,
    });
    navigateTo("/active-exam/" + examId);
  }, [dispatch, client]);

  return (
    <div className="max-w-2xl mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4">Exam Instructions</h1>
      <h1>Exam will start at: {activeExamData?.startingAt.toDateString()}</h1>
      <Button onClick={onStart}>Start Exam</Button>
    </div>
  );
}

export default ExamLobby;
