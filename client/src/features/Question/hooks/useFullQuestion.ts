import { UUID } from "crypto";
import { useEffect, useState } from "react";
import { FullQuestion } from "../types/question-types";
import { QuestionType } from "@/types/QuestionType";
import mcqQuestionApi from "../api/mcqQuestionApi";
import programmingQuestionApi from "../api/programmingQuestionApi";
import { toastMessage } from "@/utils";

function useFullQuestion(questionId: UUID, type: QuestionType) {
  const [fullQuestion, setFullQuestion] = useState<FullQuestion | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchFullQuestion = async () => {
      try {
        setLoading(true);
        let question: FullQuestion;
        if (type === QuestionType.MCQ) {
          const response = await mcqQuestionApi.getMcqQuestionById(questionId);
          question = response.data.data;
        } else {
          const response = await programmingQuestionApi.getProgrammingQuestionByQuestionId(questionId);
          question = response.data.data;
        }
        setFullQuestion(question);
      } catch (error) {
        toastMessage("Error", "Failed to fetch question details", false);
      } finally {
        setLoading(false);
      }
    };

    if (questionId) fetchFullQuestion();
  }, [questionId]);

  return { fullQuestion, loading };
}

export default useFullQuestion;
