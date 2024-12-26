import React from "react";
import { useActiveExam } from "../hooks/useActiveExam";
import QuestionDisplay from "./QuestionDisplay";
import Sidebar from "./Sidebar";
import ExamNavigation from "./ExamNavigation";

export const ExamInterface: React.FC = () => {
  const { examState, currentQuestion } = useActiveExam();

  return (
    <div className="flex flex-col h-screen bg-gray-100">
      <ExamNavigation />
      <div className="flex flex-1 overflow-hidden">
        <div className="flex-grow p-8 overflow-auto">
          {currentQuestion && (
            <QuestionDisplay
              question={currentQuestion}
              questionNumber={examState.currentQuestionIndex + 1}
            />
          )}
        </div>
        <Sidebar />
      </div>
    </div>
  );
};

export default ExamInterface;
