import mcqQuestionApi from "@/features/Question/api/mcqQuestionApi";
import { QuestionFilter } from "@/features/Question/types/question-types";
import { McqQuestionForm } from "@/features/Question/validators/mcq-validator";
import { toastApiError, toastApiSuccess } from "@/utils";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { UUID } from "crypto";

export const createMcqQuestion = createAsyncThunk(
  "questionLibrary/createMcqQuestion",
  async (mcqQuestion: McqQuestionForm) => {
    try {
      const apiRes = await mcqQuestionApi.createMcqQuestion(mcqQuestion);
      toastApiSuccess("MCQ Question created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create MCQ Question", error);
    }
  }
);

export const createBulkMcqQuestions = createAsyncThunk(
  "questionLibrary/createBulkMcqQuestions",
  async (mcqQuestions: McqQuestionForm[]) => {
    try {
      const apiRes = await mcqQuestionApi.createBulkMcqQuestions(mcqQuestions);
      toastApiSuccess("MCQ Questions created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create MCQ Questions", error);
    }
  }
);

export const getMcqQuestionById = createAsyncThunk(
  "questionLibrary/getMcqQuestionById",
  async (questionId: UUID) => {
    try {
      const apiRes = await mcqQuestionApi.getMcqQuestionById(questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch MCQ Question", error);
    }
  }
);

export const getAllMcqQuestions = createAsyncThunk(
  "questionLibrary/getAllMcqQuestions",
  async (filters: QuestionFilter) => {
    try {
      const apiRes = await mcqQuestionApi.getAllMcqQuestions(filters);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch MCQ Questions", error);
    }
  }
);
