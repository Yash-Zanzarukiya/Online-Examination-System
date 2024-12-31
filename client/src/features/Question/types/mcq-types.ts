import { UUID } from "crypto";
import { Question } from "./question-types";

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
