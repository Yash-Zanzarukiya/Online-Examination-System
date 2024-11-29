import { createAsyncThunk } from "@reduxjs/toolkit";
import { QuestionCreatePayload, QuestionForm, QuestionFilter } from "../types";
import { toastApiSuccess, toastApiError } from "@/utils";
import questionApi from "../api/questionApi";
import { UUID } from "crypto";

const {
  createQuestionWithOptionsApi,
  createMultipleQuestionsWithOptionsApi,
  createQuestionApi,
  createMultipleQuestionsApi,
  getQuestionByIdApi,
  getAllQuestionsApi,
  getAllFullQuestionsApi,
  getFullQuestionByIdApi,
  updateQuestionApi,
  deleteQuestionApi,
  deleteQuestionImageApi,
} = questionApi;

export const createFullQuestionThunk = createAsyncThunk(
  "questions/createFullQuestion",
  async (payload: QuestionCreatePayload) => {
    try {
      const apiRes = await createQuestionWithOptionsApi(payload);
      toastApiSuccess("Question Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create Question...", error);
    }
  }
);

export const createBulkFullQuestionThunk = createAsyncThunk(
  "questions/createBulkFullQuestion",
  async (payload: QuestionCreatePayload[]) => {
    try {
      const apiRes = await createMultipleQuestionsWithOptionsApi(payload);
      toastApiSuccess("All Questions Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create Multiple Questions...", error);
    }
  }
);

export const createQuestionThunk = createAsyncThunk(
  "questions/createQuestion",
  async (payload: QuestionForm) => {
    try {
      const apiRes = await createQuestionApi(payload);
      toastApiSuccess("Question Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create Question...", error);
    }
  }
);

export const createBulkQuestionsThunk = createAsyncThunk(
  "questions/createBulkQuestions",
  async (payload: QuestionForm[]) => {
    try {
      const apiRes = await createMultipleQuestionsApi(payload);
      toastApiSuccess("All Questions Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create Multiple Questions...", error);
    }
  }
);

export const getQuestionByIdThunk = createAsyncThunk(
  "questions/getById",
  async (questionId: UUID) => {
    try {
      const apiRes = await getQuestionByIdApi(questionId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch Question...", error);
    }
  }
);

export const getAllQuestionsThunk = createAsyncThunk(
  "questions/getAll",
  async (filter: QuestionFilter) => {
    try {
      const apiRes = await getAllQuestionsApi(filter);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch Questions...", error);
    }
  }
);

export const getFullQuestionByIdThunk = createAsyncThunk(
  "questions/getFullById",
  async (questionId: UUID) => {
    try {
      const apiRes = await getFullQuestionByIdApi(questionId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch Question...", error);
    }
  }
);

export const getAllFullQuestionsThunk = createAsyncThunk(
  "questions/getAllFull",
  async (filter: QuestionFilter) => {
    try {
      const apiRes = await getAllFullQuestionsApi(filter);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch Questions...", error);
    }
  }
);

export const updateQuestionThunk = createAsyncThunk(
  "questions/update",
  async ({ questionId, questionData }: { questionId: UUID; questionData: QuestionForm }) => {
    try {
      const apiRes = await updateQuestionApi(questionId, questionData);
      toastApiSuccess("Question Updated Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Update Question...", error);
    }
  }
);

export const deleteQuestionThunk = createAsyncThunk(
  "questions/delete",
  async (questionId: UUID) => {
    try {
      const apiRes = await deleteQuestionApi(questionId);
      toastApiSuccess("Question Deleted Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Delete Question...", error);
    }
  }
);

export const deleteQuestionImageThunk = createAsyncThunk(
  "questions/deleteImage",
  async (questionId: UUID) => {
    try {
      const apiRes = await deleteQuestionImageApi(questionId);
      toastApiSuccess("Question Image Deleted Successfully 🙂", apiRes);
    } catch (error: any) {
      toastApiError("Failed to Delete Question Image...", error);
    }
  }
);
