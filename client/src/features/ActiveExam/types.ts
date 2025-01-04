import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { UUID } from "crypto";

export interface ActiveExamState {
  sessionType: SessionType | null;
  activeExamData: ActiveExamData | null;
  examQuestions: ActiveExamQuestion[];
  examQuestionsState: Record<UUID, ActiveExamQuestionsState>;
  questionStartTimes: Record<UUID, number>;
  isFetchingQuestions: boolean;
  currentQuestionIndex: number;
  timeRemaining: number;
  isExamStarted: boolean;
  isExamSubmitted: boolean;
  isNewLoginDetected: boolean;
}

export interface ActiveExamData {
  status: ScheduledExamStatus;
  startingAt: Date;
}

export enum ScheduledExamStatus {
  SCHEDULED = "SCHEDULED",
  LIVE = "LIVE",
  CLOSED = "CLOSED",
}

export enum SessionType {
  NORMAL = "NORMAL",
  RESUMED = "RESUMED",
}

export interface ActiveExamQuestion {
  question: Question;
  options: Option[];
}

export interface ActiveExamQuestionsState {
  questionId: UUID;
  status: QuestionAttemptStatus;
  selectedOptionId: UUID | null;
  submittedCode: string | null;
}

export enum QuestionAttemptStatus {
  ANSWERED = "ANSWERED",
  VISITED = "VISITED",
  NOT_VISITED = "NOT_VISITED",
}

export interface Question {
  id: UUID;
  type: QuestionType;
  difficulty: Difficulty;
  questionText: string;
  image: string | null;
}

export interface Option {
  id: UUID;
  optionText: string;
  image: string | null;
}

export interface ConnectionResponse {
  sessionType: SessionType;
  activeExamData: ActiveExamData;
  timeRemaining: number;
  message: string;
}

export interface ActiveExamQuestionsResponse {
  questions: ActiveExamQuestion[];
  questionsState: Record<UUID, ActiveExamQuestionsState>;
}

export interface McqSubmissionForm {
  questionId: UUID;
  selectedOptionId: UUID;
}

export interface ProgrammingSubmissionForm {
  questionId: UUID;
  submittedCode: string;
  programmingLanguage?: string;
}

export interface QuestionTimeSpentForm {
  questionId: UUID;
  timeSpent: number;
}
