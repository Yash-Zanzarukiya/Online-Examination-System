import React from "react";
import { Button } from "@/components/ui/button";
import { useActiveExam } from "../hooks";

const ExamNavigation: React.FC = () => {
  const { examState, handleExamSubmit } = useActiveExam();

  const formatTime = (seconds: number) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes.toString().padStart(2, "0")}:${remainingSeconds.toString().padStart(2, "0")}`;
  };

  return (
    <nav className="bg-white shadow-md p-4">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">JEE Mock Exam</h1>
        <div className="flex items-center space-x-4">
          <span className="text-lg font-medium">
            Time Remaining: {formatTime(examState.timeRemaining)}
          </span>
          <Button
            onClick={handleExamSubmit}
            variant="destructive"
            className="bg-red-500 hover:bg-red-600"
          >
            Submit Exam
          </Button>
        </div>
      </div>
    </nav>
  );
};

export default ExamNavigation;
