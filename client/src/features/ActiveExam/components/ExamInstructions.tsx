import React, { useCallback, useState } from "react";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { useAppDispatch } from "@/app/hooks";
import { startExam } from "../redux/activeExamSlice";
import { useWebSocket } from "@/hooks";
import { ActionType, MessageType } from "@/features/ExamWebSocket/types/message-types";

const ExamInstructions: React.FC = () => {
  const dispatch = useAppDispatch();
  const client = useWebSocket();

  const [isAgreed, setIsAgreed] = useState(false);

  const onStart = useCallback(() => {
    client.send({ type: MessageType.ACTION, subtype: ActionType.START_EXAM_REQ, payload: {} });
    dispatch(startExam());
  }, [dispatch]);

  return (
    <div className="max-w-2xl mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4">Exam Instructions</h1>
      <ul className="list-disc pl-5 mb-6 space-y-2">
        <li>The exam consists of multiple-choice questions.</li>
        <li>Each question has one correct answer.</li>
        <li>Questions are presented one at a time.</li>
        <li>You have 60 minutes to complete the exam.</li>
        <li>You can navigate between questions using the sidebar or navigation buttons.</li>
        <li>Your answers are saved automatically when you move to another question.</li>
        <li>
          You can submit the exam at any time using the submit button in the top navigation bar.
        </li>
      </ul>
      <div className="flex items-center mb-4">
        <Checkbox
          id="agree"
          checked={isAgreed}
          onCheckedChange={(checked) => setIsAgreed(checked as boolean)}
        />
        <label
          htmlFor="agree"
          className="ml-2 text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
        >
          I have read and agree to the exam instructions
        </label>
      </div>
      <Button onClick={onStart} disabled={!isAgreed}>
        Start Exam
      </Button>
    </div>
  );
};

export default ExamInstructions;
