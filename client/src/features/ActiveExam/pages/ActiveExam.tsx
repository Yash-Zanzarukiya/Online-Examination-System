import React from "react";
import { ExamInstructions, ExamInterface } from "../components";
import { useAppSelector } from "@/app/hooks";

const ActiveExam: React.FC = () => {
  const { isExamStarted, isExamSubmitted, isFetchingQuestions } = useAppSelector(
    ({ activeExam }) => activeExam
  );

  if (!isExamStarted) return <ExamInstructions />;

  if (isFetchingQuestions) return <div>Fetching Questions...</div>;

  if (isExamSubmitted) return <div>Exam Submitted</div>;

  return <ExamInterface />;
};

export default ActiveExam;
