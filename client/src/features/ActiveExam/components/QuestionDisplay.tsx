import React from "react";
import { Button } from "@/components/ui/button";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { ActiveExamQuestion } from "../types";
import { useActiveExam } from "../hooks/useActiveExam";
import { QuestionType } from "@/types/QuestionType";
import { UUID } from "crypto";

interface QuestionDisplayProps {
  question: ActiveExamQuestion;
  questionNumber: number;
}

const QuestionDisplay: React.FC<QuestionDisplayProps> = ({ question, questionNumber }) => {
  const { examState, handleAnswerChange, handleCodeChange, handleNext, handlePrevious } =
    useActiveExam();
  const questionState = examState.examQuestionsState[question.question.id];

  const renderMCQOptions = () => (
    <RadioGroup
      onValueChange={(value) => handleAnswerChange(question.question.id, value as UUID)}
      value={questionState?.selectedOptionId || undefined}
    >
      {question.options.map((option, index) => (
        <div key={index} className="flex items-center space-x-2 mb-4">
          <RadioGroupItem value={option.id} id={`option-${index}`} />
          <Label htmlFor={`option-${index}`}>
            {option.optionText}
            {option.image && (
              <img src={option.image} alt="Option" className="mt-2 max-w-full h-auto rounded-lg" />
            )}
          </Label>
        </div>
      ))}
    </RadioGroup>
  );

  const renderCodeEditor = () => (
    <Textarea
      value={questionState?.submittedCode || ""}
      onChange={(e) => handleCodeChange(question.question.id, e.target.value)}
      className="font-mono h-64"
      placeholder="Write your code here..."
    />
  );

  return (
    <div className="max-w-4xl mx-auto bg-white rounded-lg shadow-md p-8">
      <div className="mb-6">
        <h2 className="text-2xl font-bold">Question {questionNumber}</h2>
      </div>

      <p className="text-lg mb-6">{question.question.questionText}</p>
      {question.question.image && (
        <div className="mb-6">
          <img src={question.question.image} alt="Question" className="h-48 w-auto rounded-lg" />
        </div>
      )}

      {question.question.type === QuestionType.MCQ ? renderMCQOptions() : renderCodeEditor()}

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
