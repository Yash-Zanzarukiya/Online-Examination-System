import { UUID } from "crypto";
import { ScheduledExamStatus } from "../ActiveExam/types";

export interface ExamsState {
  exam: Exam | null;
  exams: Exam[] | [];
  scheduledExam: ScheduledExam | null;
  scheduledExams: ScheduledExam[] | [];
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

export interface ScheduledExam {
  id: UUID;
  examId: UUID;
  name: string;
  collegeId: UUID;
  status: ScheduledExamStatus;
  startingAt: string;
  createdAt: string;
  updatedAt: string;
}

export interface ScheduledExamFilter {
  examId?: UUID;
  collegeId?: UUID;
  status?: ScheduledExamStatus;
}
