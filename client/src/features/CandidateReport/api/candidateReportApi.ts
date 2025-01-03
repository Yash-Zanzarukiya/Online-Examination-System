import { CandidateState, ExamAttemptStatus } from "@/features/ExamCandidates/types";
import { ApiDataResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";
import {
  CandidateExamActivity,
  McqSubmission,
  ProgrammingSubmission,
  QuestionsAnalysis,
  ScoreDistribution,
} from "../types";

class CandidateReportApi {
  private readonly baseUrl: string = "/candidate-report";

  async getCandidateState(examAttemptId: UUID): Promise<ApiDataResponse<CandidateState>> {
    return await axiosInstance.get(`${this.baseUrl}/candidate-state`, {
      params: { examAttemptId },
    });
  }

  async getCandidateScoreDistribution(
    examAttemptId: UUID
  ): Promise<ApiDataResponse<ScoreDistribution>> {
    return await axiosInstance.get(`${this.baseUrl}/score-distribution`, {
      params: { examAttemptId },
    });
  }

  async getCandidateQuestionsAnalysis(
    examAttemptId: UUID
  ): Promise<ApiDataResponse<QuestionsAnalysis[]>> {
    return await axiosInstance.get(`${this.baseUrl}/questions-analysis`, {
      params: { examAttemptId },
    });
  }

  async getMcqSubmission(
    examAttemptId: UUID,
    questionId: UUID
  ): Promise<ApiDataResponse<McqSubmission>> {
    return await axiosInstance.get(`${this.baseUrl}/mcq-submission`, {
      params: { examAttemptId, questionId },
    });
  }

  async getProgrammingSubmission(
    examAttemptId: UUID,
    questionId: UUID
  ): Promise<ApiDataResponse<ProgrammingSubmission>> {
    return await axiosInstance.get(`${this.baseUrl}/programming-submission`, {
      params: { examAttemptId, questionId },
    });
  }

  async getExamActivities(examAttemptId: UUID): Promise<ApiDataResponse<CandidateExamActivity[]>> {
    return await axiosInstance.get(`${this.baseUrl}/exam-activities`, {
      params: { examAttemptId },
    });
  }

  async updateCandidateStatus(
    examAttemptId: UUID,
    status: ExamAttemptStatus
  ): Promise<ApiDataResponse<null>> {
    return await axiosInstance.patch(`/exam-attempt/status`, null, {
      params: { examAttemptId, status },
    });
  }

  async updateProgrammingMarks(
    programmingSubmissionId: UUID,
    marks: number
  ): Promise<ApiDataResponse<null>> {
    return await axiosInstance.patch(`/exam-attempt/programming-marks`, null, {
      params: { programmingSubmissionId, marks },
    });
  }
}

const candidateReportApi = new CandidateReportApi();

export default candidateReportApi;
