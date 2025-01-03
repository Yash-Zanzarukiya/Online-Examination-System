import { CategoryDTO } from "@/types/Category";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { UUID } from "crypto";
import { McqOption } from "./mcq-types";

export interface Question {
  id: UUID;
  category: CategoryDTO;
  difficulty: Difficulty;
  type: QuestionType;
  questionText: string;
  image: string;
  marks: number;
}

export interface QuestionFilter {
  categoryId?: UUID;
  difficulty?: Difficulty;
  type?: QuestionType;
}

export type FullQuestion = {
  question: Question;
  options?: McqOption[];
  referenceAnswer?: string;
};
