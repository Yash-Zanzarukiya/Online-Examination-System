import React from "react";
import { Button } from "@/components/ui/button";
import { ActiveExamQuestion } from "../types";
import { useActiveExam } from "../hooks/useActiveExam";
import { QuestionType } from "@/types/QuestionType";
import { McqOptions, ProgrammingQuestionCodeEditor } from ".";

interface QuestionDisplayProps {
  question: ActiveExamQuestion;
  questionNumber: number;
}

const QuestionDisplay: React.FC<QuestionDisplayProps> = ({ question, questionNumber }) => {
  const { examState, handleAnswerChange, handleCodeChange, handleNext, handlePrevious } =
    useActiveExam();

  const questionState = examState.examQuestionsState[question.question.id];

  return (
    <div
      className={`max-w-4xl ${
        question.question.type === QuestionType.PROGRAMMING && "max-w-6xl"
      } mx-auto rounded-lg shadow-md p-8`}
    >
      <div className="mb-6">
        <h2 className="text-2xl font-bold">Question {questionNumber}</h2>
      </div>

      <p className="text-lg mb-6">{question.question.questionText}</p>
      {question.question.image && (
        <div className="mb-6">
          <img src={question.question.image} alt="Question" className="h-48 w-auto rounded-lg" />
        </div>
      )}

      {question.question.type === QuestionType.MCQ ? (
        <McqOptions
          key={question.question.id}
          handleAnswerChange={handleAnswerChange}
          options={question.options}
          questionId={question.question.id}
          selectedOptionId={questionState?.selectedOptionId}
        />
      ) : (
        <ProgrammingQuestionCodeEditor
          key={question.question.id}
          questionId={question.question.id}
          submittedCode={questionState?.submittedCode}
          onChange={handleCodeChange}
        />
      )}

      <div className="flex justify-between mt-8">
        <Button onClick={handlePrevious} disabled={questionNumber === 1}>
          Previous
        </Button>
        <Button onClick={handleNext} disabled={questionNumber === examState.examQuestions.length}>
          Next
        </Button>
      </div>
    </div>
  );
};

export default QuestionDisplay;
