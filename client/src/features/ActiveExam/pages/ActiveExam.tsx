import React from "react";
import { ExamInstructions, ExamInterface } from "../components";
import { useAppSelector } from "@/app/hooks";
import ExamCompleted from "../components/ExamResults";

const ActiveExam: React.FC = () => {
  const { isExamStarted, isExamSubmitted, isFetchingQuestions } = useAppSelector(
    ({ activeExam }) => activeExam
  );

  if (!isExamStarted) return <ExamInstructions />;

  if (isFetchingQuestions) return <div>Fetching Questions...</div>;

  if (isExamSubmitted) return <ExamCompleted />;

  return <ExamInterface />;
};

export default ActiveExam;
