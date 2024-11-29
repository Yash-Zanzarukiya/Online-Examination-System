import { CategoryDTO } from "@/types/Category";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { UUID } from "crypto";

export interface QuestionState {
  questions: FullQuestion[] | [];
  question: FullQuestion | {};
  isLoading: boolean;
}

export interface Question {
  id: UUID;
  category: CategoryDTO;
  difficulty: Difficulty;
  type: QuestionType;
  questionText: string;
  image: string;
  correctAnswerId: UUID;
}

export interface McqOption {
  id: UUID;
  questionId: UUID;
  optionText: string;
  image: string;
  correct: boolean;
}

export interface FullQuestion {
  question: Question;
  options?: McqOption[] | [];
}

export interface QuestionForm {
  id?: UUID;
  categoryId?: UUID;
  difficulty: Difficulty;
  type: QuestionType;
  questionText: string;
  imageUrl?: string;
  imageFile?: File;
  correctAnswerId?: UUID;
}

export interface McqOptionForm {
  id?: UUID;
  questionId?: UUID;
  optionText: string;
  imageUrl?: string;
  imageFile?: File;
  isCorrect: boolean;
}

export interface QuestionCreatePayload {
  question: QuestionForm;
  options: McqOptionForm[];
}

export interface QuestionFilter {
  categoryId?: UUID;
  difficulty?: Difficulty;
  type?: QuestionType;
}
