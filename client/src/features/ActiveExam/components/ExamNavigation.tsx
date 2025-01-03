import React from "react";
import { Button } from "@/components/ui/button";
import { useActiveExam } from "../hooks";
import ConfirmDialog from "@/components/custom/ConfirmDialog";

const ExamNavigation: React.FC = () => {
  const { examState, handleExamSubmit } = useActiveExam();

  const formatTime = (seconds: number) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes.toString().padStart(2, "0")}:${remainingSeconds.toString().padStart(2, "0")}`;
  };

  return (
    <nav className="bg-white shadow-md p-4">
      <div className="mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">Online Examination</h1>
        <div className="flex items-center space-x-8">
          <span className="text-lg font-medium">
            Time Remaining: {formatTime(examState.timeRemaining)}
          </span>

          <ConfirmDialog
            onConfirm={handleExamSubmit}
            title="Confirm Submit Exam"
            description="Once submitted, you are no longer view or modify this exam. Are you sure you are done and want to submit the exam?"
            confirmText="Yes, submit"
          >
            <Button variant="destructive">Submit Exam</Button>
          </ConfirmDialog>
        </div>
      </div>
    </nav>
  );
};

export default ExamNavigation;
