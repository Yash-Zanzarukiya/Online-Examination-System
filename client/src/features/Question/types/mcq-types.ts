import { Question } from "@/features/QuestionBuilder/types";
import { UUID } from "crypto";

export interface McqQuestion {
  question: Question;
  options: McqOption[];
}

export interface McqOption {
  id: UUID;
  questionId: UUID;
  optionText: string;
  image: string;
  correct: boolean;
}
