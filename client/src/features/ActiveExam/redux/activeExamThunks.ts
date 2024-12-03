import { createAsyncThunk } from "@reduxjs/toolkit";
import activeExamApi from "../api/activeExamApi";
import { UUID } from "crypto";
import { toastApiError, toastApiSuccess } from "@/utils";

export const getQuestionsForExam = createAsyncThunk(
  "activeExam/getAllQuestions",
  async (examId: UUID) => {
    try {
      const apiRes = await activeExamApi.getQuestionsForExam(examId);
      toastApiSuccess("Exam questions fetched successfully...");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch exam questions...", error);
    }
  }
);
