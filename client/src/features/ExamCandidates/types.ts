import { UUID } from "crypto";

export interface ExamCandidatesState {
  candidates: CandidateState[] | [];
  isLoading: boolean;
}

export enum ExamAttemptStatus {
  NOT_STARTED = "NOT_STARTED",
  IN_PROGRESS = "IN_PROGRESS",
  INTERRUPTED = "INTERRUPTED",
  TERMINATED = "TERMINATED",
  SUBMITTED = "SUBMITTED",
  TO_EVALUATE = "TO_EVALUATE",
  PASSED = "PASSED",
  FAILED = "FAILED",
  QUALIFIED = "QUALIFIED",
}

export interface CandidateState {
  examAttemptId: UUID;
  userId: UUID;
  email: string;
  candidateName: string;
  status: ExamAttemptStatus;
  score: number;
  totalMarks: number;
  startTime: string;
  endTime: string;
}
