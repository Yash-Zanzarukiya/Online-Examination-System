import React from "react";
import { ExamState, ExamQuestion } from "../types";

interface ExamResultsProps {
  examState: ExamState;
  examQuestions: ExamQuestion[];
}

const ExamResults: React.FC<ExamResultsProps> = ({ examState, examQuestions }) => {
  const calculateScore = () => {
    let correctAnswers = 0;
    examQuestions.forEach((question) => {
      if (examState.answers[question.question.id] === question.options[0].id) {
        correctAnswers++;
      }
    });
    return correctAnswers;
  };

  const score = calculateScore();
  const totalQuestions = examQuestions.length;
  const percentage = (score / totalQuestions) * 100;

  return (
    <div className="max-w-2xl mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4">Exam Results</h1>
      <div className="mb-6">
        <p className="text-xl">
          Your Score: {score} / {totalQuestions} ({percentage.toFixed(2)}%)
        </p>
      </div>
      <h2 className="text-xl font-bold mb-4">Incorrect Answers:</h2>
      {examQuestions.map((question, index) => {
        const isCorrect = examState.answers[question.question.id] === question.options[0].id;
        if (isCorrect) return null;

        return (
          <div key={question.question.id} className="mb-6 p-4 bg-gray-100 rounded">
            <h3 className="font-bold mb-2">Question {index + 1}:</h3>
            <p className="mb-2">{question.question.questionText}</p>
            <p className="text-red-500">
              Your Answer:{" "}
              {
                question.options.find((o) => o.id === examState.answers[question.question.id])
                  ?.optionText
              }
            </p>
            <p className="text-green-500">Correct Answer: {question.options[0].optionText}</p>
          </div>
        );
      })}
    </div>
  );
};

export default ExamResults;
