import { UUID } from "crypto";
import { Question } from "../Question/types/question-types";

export interface QuestionPickerState {
  examId?: UUID;
  initialExamQuestions: Question[] | [];
  examQuestions: ExamQuestion[] | [];
  allQuestions: Question[] | [];
  isLoading: boolean;
}

export interface ExamQuestion {
  id: UUID;
  examId?: UUID;
  questionId: UUID;
}

export interface examQuestionsForm {
  examId: UUID;
  questionIds: UUID[];
}

export interface ExamQuestionResponse {
  id: UUID;
  examId: UUID;
  questionId: UUID;
}

export interface getExamQuestionsResponse {
  id: UUID;
  question: Question;
}
