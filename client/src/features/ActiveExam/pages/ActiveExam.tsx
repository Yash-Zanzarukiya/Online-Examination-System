import React from "react";
import { ExamInstructions, ExamInterface, WarningDialog } from "../components";
import { useAppSelector } from "@/app/hooks";
import ExamCompleted from "../components/ExamResults";
import { navigateTo } from "@/utils";

const ActiveExam: React.FC = () => {
  const { isExamStarted, isExamSubmitted, isFetchingQuestions, isNewLoginDetected } =
    useAppSelector(({ activeExam }) => activeExam);

  if (!isExamStarted) return <ExamInstructions />;

  if (isFetchingQuestions) return <div>Fetching Questions...</div>;

  if (isExamSubmitted) return <ExamCompleted />;

  return (
    <>
      <ExamInterface />
      <WarningDialog
        title="New Login Detected to exam"
        isOpen={isNewLoginDetected}
        onClose={() => navigateTo("/")}
        description="You have been logged out from this exam as you have logged in from another device"
      />
    </>
  );
};

export default ActiveExam;
