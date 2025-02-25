import { Header, QuestionComponent } from "../components";
import { useState } from "react";
import { QuestionCreatePayload } from "../types";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { Button } from "@/components/ui/button";
import { AddQuestionButton } from "@/features/QuestionsLibrary/components";

export default function QuestionCreator() {
  const [questions, setQuestions] = useState<QuestionCreatePayload[]>([]);

  const handleAddQuestion = () => {
    setQuestions((prev) => [
      ...prev,
      {
        question: { difficulty: Difficulty.EASY, type: QuestionType.MCQ, questionText: "" },
        options: [
          { optionText: "", isCorrect: true },
          { optionText: "", isCorrect: false },
        ],
      },
    ]);
  };

  const handleUpdateQuestion = (index: number, data: QuestionCreatePayload) => {
    setQuestions((prev) => prev.map((question, i) => (i === index ? data : question)));
  };

  const handleRemoveQuestion = (index: number) => {
    setQuestions((prev) => prev.filter((_, i) => i !== index));
  };

  const handleSubmitAll = async () => {
    console.log("Submitting all questions:", questions);
  };

  return (
    <div className="container mx-auto p-6 max-w-5xl space-y-4">
      <Header />

      <AddQuestionButton />

      {questions.map((data, index) => (
        <div key={index} className="relative">
          <QuestionComponent
            initialData={data}
            onUpdate={(updatedData) => handleUpdateQuestion(index, updatedData)}
          />
        </div>
      ))}
      <div className="flex gap-4">
        <Button onClick={handleAddQuestion}>Add Question</Button>
        {/* {questions.length > 1 && <Button onClick={handleSubmitAll}>Submit All Questions</Button>} */}
      </div>
    </div>
  );
}
