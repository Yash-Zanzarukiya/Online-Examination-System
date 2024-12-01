import { UUID } from "crypto";

export interface ExamsState {
  exams: Exam[] | [];
  exam: Exam | null;
  isLoading: boolean;
}

export interface Exam {
  id: UUID;
  title: string;
  passingScore: number;
  timeLimit: number;
  createdAt: string;
  updatedAt: string;
}

export interface ExamForm {
  title: string;
  passingScore: number;
  timeLimit: number;
}
