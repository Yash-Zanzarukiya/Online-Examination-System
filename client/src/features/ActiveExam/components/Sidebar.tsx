import React from "react";
import { Button } from "@/components/ui/button";
import { useActiveExam } from "../hooks";

const Sidebar: React.FC = () => {
  const { examState, questions, handleQuestionNavigation } = useActiveExam();

  const remainingQuestions = Object.values(examState.questionStates).filter(
    (state) => state !== "answered"
  ).length;

  return (
    <div className="w-64 bg-white shadow-md">
      <div className="h-full overflow-auto">
        <div className="p-4">
          <h2 className="text-lg font-semibold mb-4">Questions</h2>
          <div className="grid grid-cols-5 gap-2">
            {questions.map((question, index) => {
              const state = examState.questionStates[question.question.id];
              return (
                <Button
                  key={index}
                  variant="outline"
                  className={`w-10 h-10 ${
                    state === "answered"
                      ? "bg-green-500 text-white"
                      : state === "attempted"
                      ? "bg-yellow-500 text-white"
                      : "bg-gray-300"
                  } ${index === examState.currentQuestionIndex ? "ring-2 ring-blue-500" : ""}`}
                  onClick={() => handleQuestionNavigation(index)}
                >
                  {index + 1}
                </Button>
              );
            })}
          </div>
          <div className="mt-6 space-y-2">
            <div className="flex items-center">
              <div className="w-4 h-4 bg-green-500 rounded-full mr-2"></div>
              <span>Answered</span>
            </div>
            <div className="flex items-center">
              <div className="w-4 h-4 bg-yellow-500 rounded-full mr-2"></div>
              <span>Attempted</span>
            </div>
            <div className="flex items-center">
              <div className="w-4 h-4 bg-gray-300 rounded-full mr-2"></div>
              <span>Not Attempted</span>
            </div>
          </div>
          <div className="mt-6 p-4 bg-blue-100 rounded-lg">
            <p className="font-semibold text-blue-800">Remaining Questions: {remainingQuestions}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
