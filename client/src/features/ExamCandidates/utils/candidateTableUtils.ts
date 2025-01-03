import { CandidateState, ExamAttemptStatus } from "../types";
import { format } from "date-fns";

export const getStatusColor = (status: ExamAttemptStatus) => {
  switch (status) {
    case ExamAttemptStatus.NOT_STARTED:
      return "text-gray-500 ";
    case ExamAttemptStatus.IN_PROGRESS:
      return "text-blue-500 ";
    case ExamAttemptStatus.INTERRUPTED:
      return "text-pink-500 ";
    case ExamAttemptStatus.TERMINATED:
      return "text-black-500 ";
    case ExamAttemptStatus.SUBMITTED:
      return "text-orange-500 ";
    case ExamAttemptStatus.TO_EVALUATE:
      return "text-purple-500 ";
    case ExamAttemptStatus.PASSED:
      return "text-green-500 ";
    case ExamAttemptStatus.FAILED:
      return "text-red-500 ";
    default:
      return "text-gray-500 ";
  }
};

export const getTimelineText = (attempt: CandidateState) => {
  switch (attempt.status) {
    case ExamAttemptStatus.NOT_STARTED:
      return "Not started yet";
    case ExamAttemptStatus.IN_PROGRESS:
      return `Started on ${format(new Date(attempt.startTime), "dd MMM, yyyy")}`;
    case ExamAttemptStatus.TO_EVALUATE:
      return `Completed on ${format(new Date(attempt.endTime), "dd MMM, yyyy")}`;
    case ExamAttemptStatus.INTERRUPTED:
      return "Candidate is disconnected";
    case ExamAttemptStatus.TERMINATED:
      return "Candidate is disqualified";
    default:
      return `Completed on ${format(new Date(attempt.endTime), "dd MMM, yyyy")}`;
  }
};
