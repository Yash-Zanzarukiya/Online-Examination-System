import React from "react";
import { Button } from "@/components/ui/button";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";
import { ExamQuestion } from "../types";

interface QuestionDisplayProps {
  question: ExamQuestion;
  questionNumber: number;
  selectedAnswer: string | undefined;
  onAnswerChange: (answerId: string) => void;
  onPrevious: () => void;
  onNext: () => void;
  onSave: () => void;
  isFirst: boolean;
  isLast: boolean;
}

const QuestionDisplay: React.FC<QuestionDisplayProps> = ({
  question,
  questionNumber,
  selectedAnswer,
  onAnswerChange,
  onPrevious,
  onNext,
  onSave,
  isFirst,
  isLast,
}) => {
  return (
    <div className="max-w-4xl mx-auto bg-white rounded-lg shadow-md p-8">
      <div className="mb-6">
        <h2 className="text-2xl font-bold">Question {questionNumber}</h2>
      </div>
      <p className="text-lg mb-6">{question.question.questionText}</p>
      {question.question.image && (
        <div className="mb-6">
          <img
            src={question.question.image}
            alt="Question"
            className="max-w-full h-auto rounded-lg"
          />
        </div>
      )}
      <RadioGroup onValueChange={onAnswerChange} value={selectedAnswer}>
        {question.options.map((option, index) => (
          <div key={index} className="flex items-center space-x-2 mb-4">
            <RadioGroupItem value={option.id} id={`option-${index}`} />
            <Label htmlFor={`option-${index}`}>
              {option.optionText}
              {option.image && (
                <img
                  src={option.image}
                  alt="Option"
                  className="mt-2 max-w-full h-auto rounded-lg"
                />
              )}
            </Label>
          </div>
        ))}
      </RadioGroup>
      <div className="flex justify-between mt-8">
        <Button onClick={onPrevious} disabled={isFirst}>
          Previous
        </Button>
        <div className="space-x-2">
          <Button
            onClick={onSave}
            disabled={!selectedAnswer}
            className="bg-green-500 hover:bg-green-600 text-white"
          >
            Save
          </Button>
          <Button onClick={onNext} disabled={isLast}>
            Next
          </Button>
        </div>
      </div>
    </div>
  );
};

export default QuestionDisplay;
