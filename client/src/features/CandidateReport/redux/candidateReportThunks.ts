import { createAsyncThunk } from "@reduxjs/toolkit";
import { toastApiError, toastApiSuccess } from "@/utils";
import candidateReportApi from "../api/candidateReportApi";
import { UUID } from "crypto";
import { ExamAttemptStatus } from "@/features/ExamCandidates/types";
import { updateExamCandidateStatus } from "@/features/ExamCandidates/redux/examCandidatesSlice";

export const getCandidateState = createAsyncThunk(
  "candidateReport/getCandidateState",
  async (examAttemptId: UUID) => {
    try {
      const apiRes = await candidateReportApi.getCandidateState(examAttemptId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch candidate state", error);
    }
  }
);

export const getCandidateScoreDistribution = createAsyncThunk(
  "candidateReport/getCandidateScoreDistribution",
  async (examAttemptId: UUID) => {
    try {
      const apiRes = await candidateReportApi.getCandidateScoreDistribution(examAttemptId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch score distribution", error);
    }
  }
);

export const getCandidateQuestionsAnalysis = createAsyncThunk(
  "candidateReport/getCandidateQuestionsAnalysis",
  async (examAttemptId: UUID) => {
    try {
      const apiRes = await candidateReportApi.getCandidateQuestionsAnalysis(examAttemptId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch questions analysis", error);
    }
  }
);

export const getMcqSubmission = createAsyncThunk(
  "candidateReport/getMcqSubmission",
  async ({ examAttemptId, questionId }: { examAttemptId: UUID; questionId: UUID }) => {
    try {
      const apiRes = await candidateReportApi.getMcqSubmission(examAttemptId, questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch MCQ submission", error);
    }
  }
);

export const getProgrammingSubmission = createAsyncThunk(
  "candidateReport/getProgrammingSubmission",
  async ({ examAttemptId, questionId }: { examAttemptId: UUID; questionId: UUID }) => {
    try {
      const apiRes = await candidateReportApi.getProgrammingSubmission(examAttemptId, questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch programming submission", error);
    }
  }
);

export const updateCandidateStatus = createAsyncThunk(
  "candidateReport/updateExamAttemptStatus",
  async (
    { examAttemptId, status }: { examAttemptId: UUID; status: ExamAttemptStatus },
    { dispatch }
  ) => {
    try {
      const apiRes = await candidateReportApi.updateCandidateStatus(examAttemptId, status);
      dispatch(
        updateExamCandidateStatus({
          examAttemptId,
          status,
        })
      );
      toastApiSuccess("Success 🙂", apiRes);
      return status;
    } catch (error) {
      toastApiError("Failed to update exam attempt status", error);
    }
  }
);

export const updateProgrammingMarks = createAsyncThunk(
  "candidateReport/updateProgrammingMarks",
  async ({ programmingSubmissionId, marks }: { programmingSubmissionId: UUID; marks: number }) => {
    try {
      const apiRes = await candidateReportApi.updateProgrammingMarks(
        programmingSubmissionId,
        marks
      );
      toastApiSuccess("Success 🙂", apiRes);
      return { programmingSubmissionId, marks };
    } catch (error) {
      toastApiError("Failed to update programming marks", error);
    }
  }
);
