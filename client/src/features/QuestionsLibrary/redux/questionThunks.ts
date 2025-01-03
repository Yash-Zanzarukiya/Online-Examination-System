import questionApi from "@/features/Question/api/questionApi";
import { QuestionFilter } from "@/features/Question/types/question-types";
import { QuestionForm } from "@/features/Question/validators/question-validator";
import { toastApiError, toastApiSuccess } from "@/utils";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { UUID } from "crypto";

export const createQuestion = createAsyncThunk(
  "questionLibrary/createQuestion",
  async (question: QuestionForm) => {
    try {
      const apiRes = await questionApi.createQuestion(question);
      toastApiSuccess("Question created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create question", error);
    }
  }
);

export const createBulkQuestions = createAsyncThunk(
  "questionLibrary/createBulkQuestions",
  async (questions: QuestionForm[]) => {
    try {
      const apiRes = await questionApi.createBulkQuestions(questions);
      toastApiSuccess("Questions created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create questions", error);
    }
  }
);

export const uploadQuestions = createAsyncThunk(
  "questionLibrary/uploadQuestions",
  async (formData: FormData) => {
    try {
      const apiRes = await questionApi.uploadQuestions(formData);
      toastApiSuccess("Questions uploaded successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to upload questions", error);
    }
  }
);

export const getQuestionById = createAsyncThunk(
  "questionLibrary/getQuestionById",
  async (questionId: UUID) => {
    try {
      const apiRes = await questionApi.getQuestionById(questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch question", error);
    }
  }
);

export const getAllQuestions = createAsyncThunk(
  "questionLibrary/getAllQuestions",
  async (filters?: QuestionFilter) => {
    try {
      const apiRes = await questionApi.getAllQuestions(filters);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch questions", error);
    }
  }
);

export const updateQuestion = createAsyncThunk(
  "questionLibrary/updateQuestion",
  async ({ questionId, questionData }: { questionId: UUID; questionData: QuestionForm }) => {
    try {
      const apiRes = await questionApi.updateQuestion(questionId, questionData);
      toastApiSuccess("Question updated successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update question", error);
    }
  }
);

export const deleteQuestion = createAsyncThunk(
  "questionLibrary/deleteQuestion",
  async (questionId: UUID) => {
    try {
      await questionApi.deleteQuestion(questionId);
      toastApiSuccess("Question deleted successfully");
      return questionId;
    } catch (error) {
      toastApiError("Failed to delete question", error);
    }
  }
);

export const deleteQuestionImage = createAsyncThunk(
  "questionLibrary/deleteQuestionImage",
  async (questionId: UUID) => {
    try {
      await questionApi.deleteQuestionImage(questionId);
      toastApiSuccess("Question image deleted successfully");
      return questionId;
    } catch (error) {
      toastApiError("Failed to delete question image", error);
    }
  }
);
