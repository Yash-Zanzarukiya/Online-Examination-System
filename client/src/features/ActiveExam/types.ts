import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";

export interface ActiveExamState {
  examQuestions: ActiveExamQuestion[];
  isFetchingQuestions: boolean;
  currentQuestionIndex: number;
  answers: Record<string, string>;
  questionStates: Record<string, "not-attempted" | "attempted" | "answered">;
  timeRemaining: number;
  isInstructionsAgreed: boolean;
  isExamStarted: boolean;
  isExamSubmitted: boolean;
}

export interface ActiveExamQuestion {
  question: Question;
  options: Option[];
}

export interface Question {
  id: string;
  type: QuestionType;
  difficulty: Difficulty;
  questionText: string;
  image: string | null;
}

export interface Option {
  id: string;
  optionText: string;
  image: string | null;
}
