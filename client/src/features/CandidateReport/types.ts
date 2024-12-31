import { UUID } from "crypto";
import { McqQuestion } from "../Question/types/mcq-types";
import { ProgrammingQuestion } from "../Question/types/programming-types";
import { CandidateState } from "../ExamCandidates/types";

export interface CandidateReportState {
  candidateState: CandidateState | null;
  scoreDistribution: ScoreDistribution | null;
  questionsAnalysis: QuestionsAnalysis[];
  isLoading: boolean;
}

export interface McqSubmission {
  question: McqQuestion;
  selectedOptionId: UUID;
  score: number;
}

export interface ProgrammingSubmission {
  id: UUID;
  question: ProgrammingQuestion;
  submittedCode: string;
  score: number;
}

export interface QuestionPerformance {
  questionId: UUID;
  questionText: string;
  status: QuestionEvaluationStatus;
  score: number;
  totalMarks: number;
  timeTaken: number;
  category: string | null;
}

export enum QuestionEvaluationStatus {
  CORRECT = "CORRECT",
  INCORRECT = "INCORRECT",
  PARTIALLY_CORRECT = "PARTIALLY_CORRECT",
  NOT_ANSWERED = "NOT_ANSWERED",
}

export interface QuestionsAnalysis {
  sectionName: string;
  totalQuestions: number;
  attemptedQuestions: number;
  score: number;
  totalMarks: number;
  timeTaken: number;
  questionPerformances: QuestionPerformance[];
}

export interface ScoreDistribution {
  aggregatedMarks: AggregatedMarks;
  aggregatedScores: AggregatedScores;
}

export interface AggregatedMarks {
  totalMarks: number;
  mcqQuestionTotalMarks: number;
  programmingQuestionTotalMarks: number;
  easyQuestionTotalMarks: number;
  mediumQuestionTotalMarks: number;
  hardQuestionTotalMarks: number;
  aptitudeMcqTotalMarks: number;
  technicalMcqTotalMarks: number;
  programmingMcqTotalMarks: number;
}

export interface AggregatedScores {
  totalScore: number;
  mcqQuestionScore: number;
  programmingQuestionScore: number;
  easyQuestionScore: number;
  mediumQuestionScore: number;
  hardQuestionScore: number;
  aptitudeMcqScore: number;
  technicalMcqScore: number;
  programmingMcqScore: number;
}
