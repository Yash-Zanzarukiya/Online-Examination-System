import { toastApiError, toastApiSuccess } from "@/utils";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { UUID } from "crypto";
import { Difficulty } from "@/types/Difficulty";
import { ProgrammingQuestionForm } from "@/features/Question/validators/programming-validator";
import programmingQuestionApi from "@/features/Question/api/programmingQuestionApi";

export const createProgrammingQuestion = createAsyncThunk(
  "questionLibrary/createProgrammingQuestion",
  async (data: ProgrammingQuestionForm) => {
    try {
      const apiRes = await programmingQuestionApi.createProgrammingQuestion(data);
      toastApiSuccess("Programming question created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create programming question", error);
    }
  }
);

export const createBulkProgrammingQuestions = createAsyncThunk(
  "questionLibrary/createBulkProgrammingQuestions",
  async (data: ProgrammingQuestionForm[]) => {
    try {
      const apiRes = await programmingQuestionApi.createBulkProgrammingQuestions(data);
      toastApiSuccess("Programming questions created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create programming questions", error);
    }
  }
);

export const getProgrammingQuestionById = createAsyncThunk(
  "questionLibrary/getProgrammingQuestionById",
  async (id: UUID) => {
    try {
      const apiRes = await programmingQuestionApi.getProgrammingQuestionById(id);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch programming question", error);
    }
  }
);

export const getProgrammingQuestionByQuestionId = createAsyncThunk(
  "questionLibrary/getProgrammingQuestionByQuestionId",
  async (questionId: UUID) => {
    try {
      const apiRes = await programmingQuestionApi.getProgrammingQuestionByQuestionId(questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch programming question by question ID", error);
    }
  }
);

export const getAllProgrammingQuestions = createAsyncThunk(
  "questionLibrary/getAllProgrammingQuestions",
  async (difficulty?: Difficulty) => {
    try {
      const apiRes = await programmingQuestionApi.getAllProgrammingQuestions(difficulty);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch programming questions", error);
    }
  }
);

export const updateProgrammingQuestion = createAsyncThunk(
  "questionLibrary/updateProgrammingQuestion",
  async ({ questionId, data }: { questionId: UUID; data: ProgrammingQuestionForm }) => {
    try {
      const apiRes = await programmingQuestionApi.updateProgrammingQuestion(questionId, data);
      toastApiSuccess("Programming question updated successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update programming question", error);
    }
  }
);

export const deleteProgrammingQuestion = createAsyncThunk(
  "questionLibrary/deleteProgrammingQuestion",
  async (questionId: UUID) => {
    try {
      await programmingQuestionApi.deleteProgrammingQuestion(questionId);
      toastApiSuccess("Programming question deleted successfully");
      return questionId;
    } catch (error) {
      toastApiError("Failed to delete programming question", error);
    }
  }
);
