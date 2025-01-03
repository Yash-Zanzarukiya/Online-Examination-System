import React from "react";
import { useActiveExam } from "../hooks/useActiveExam";
import QuestionDisplay from "./QuestionDisplay";
import Sidebar from "./Sidebar";
import ExamNavigation from "./ExamNavigation";
import { useExamSecurity } from "../hooks";

export const ExamInterface: React.FC = () => {
  const { examState, currentQuestion } = useActiveExam();
  const { WarningDialogComponent } = useExamSecurity();

  return (
    <div id="exam-container" className="flex flex-col h-screen bg-gray-100">
      <ExamNavigation />
      <div className="flex flex-1 overflow-hidden">
        <div id="question-container" className="flex-grow p-8 overflow-auto">
          {currentQuestion && (
            <QuestionDisplay
              question={currentQuestion}
              questionNumber={examState.currentQuestionIndex + 1}
            />
          )}
        </div>
        <Sidebar />
      </div>
      {WarningDialogComponent}
    </div>
  );
};

export default ExamInterface;
