import { Question } from "@/features/QuestionBuilder/types";
import { useState } from "react";

function useQuestionDialog() {
  const [dialogQuestion, setDialogQuestion] = useState<Question | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const handleQuestionClick = (sq: Question) => {
    setDialogQuestion(sq);
    setIsDialogOpen(true);
  };

  return { dialogQuestion, isDialogOpen, handleQuestionClick, setIsDialogOpen };
}

export default useQuestionDialog;
