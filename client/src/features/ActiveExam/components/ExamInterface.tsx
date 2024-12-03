import React from "react";
import { Sidebar, QuestionDisplay, ExamNavigation } from "../components";
import { useActiveExam } from "../hooks";

export const ExamInterface: React.FC = () => {
  const {
    examState,
    currentQuestion,
    questions,
    handleAnswerChange,
    handleQuestionNavigation,
    handleSaveAndNext,
  } = useActiveExam();

  return (
    <div className="flex flex-col h-screen bg-gray-100">
      <ExamNavigation />
      <div className="flex flex-1 overflow-hidden">
        <div className="flex-grow p-8 overflow-auto">
          <QuestionDisplay
            question={currentQuestion}
            questionNumber={examState.currentQuestionIndex + 1}
            selectedAnswer={examState.answers[currentQuestion.question.id]}
            onAnswerChange={(answerId) => handleAnswerChange(currentQuestion.question.id, answerId)}
            onPrevious={() => handleQuestionNavigation(examState.currentQuestionIndex - 1)}
            onNext={() => handleQuestionNavigation(examState.currentQuestionIndex + 1)}
            onSave={handleSaveAndNext}
            isFirst={examState.currentQuestionIndex === 0}
            isLast={examState.currentQuestionIndex === questions.length - 1}
          />
        </div>
        <Sidebar />
      </div>
    </div>
  );
};

export default ExamInterface;
