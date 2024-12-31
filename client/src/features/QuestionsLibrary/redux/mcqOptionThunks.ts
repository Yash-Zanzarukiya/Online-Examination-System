import mcqOptionApi from "@/features/Question/api/mcqOptionApi";
import { McqOptionForm } from "@/features/Question/validators/mcq-validator";
import { toastApiError, toastApiSuccess } from "@/utils";
import { createAsyncThunk } from "@reduxjs/toolkit";
import { UUID } from "crypto";

export const createMcqOption = createAsyncThunk(
  "questionLibrary/createMcqOption",
  async (data: McqOptionForm) => {
    try {
      const apiRes = await mcqOptionApi.createMcqOption(data);
      toastApiSuccess("MCQ Option created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create MCQ Option", error);
    }
  }
);

export const createBulkMcqOptions = createAsyncThunk(
  "questionLibrary/createBulkMcqOptions",
  async (data: McqOptionForm[]) => {
    try {
      const apiRes = await mcqOptionApi.createBulkMcqOptions(data);
      toastApiSuccess("Bulk MCQ Options created successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to create bulk MCQ Options", error);
    }
  }
);

export const getMcqOptionById = createAsyncThunk(
  "questionLibrary/getMcqOptionById",
  async (mcqOptionId: UUID) => {
    try {
      const apiRes = await mcqOptionApi.getMcqOptionById(mcqOptionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch MCQ Option", error);
    }
  }
);

export const getAllMcqOptionsByQuestionId = createAsyncThunk(
  "questionLibrary/getAllMcqOptionsByQuestionId",
  async (questionId: UUID) => {
    try {
      const apiRes = await mcqOptionApi.getAllMcqOptionsByQuestionId(questionId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch MCQ Options", error);
    }
  }
);

export const updateMcqOption = createAsyncThunk(
  "questionLibrary/updateMcqOption",
  async ({ mcqOptionId, data }: { mcqOptionId: UUID; data: McqOptionForm }) => {
    try {
      const apiRes = await mcqOptionApi.updateMcqOption(mcqOptionId, data);
      toastApiSuccess("MCQ Option updated successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update MCQ Option", error);
    }
  }
);

export const updateBulkMcqOptions = createAsyncThunk(
  "questionLibrary/updateBulkMcqOptions",
  async (data: McqOptionForm[]) => {
    try {
      const apiRes = await mcqOptionApi.updateBulkMcqOptions(data);
      toastApiSuccess("Bulk MCQ Options updated successfully");
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update bulk MCQ Options", error);
    }
  }
);

export const deleteMcqOption = createAsyncThunk(
  "questionLibrary/deleteMcqOption",
  async (mcqOptionId: UUID) => {
    try {
      await mcqOptionApi.deleteMcqOption(mcqOptionId);
      toastApiSuccess("MCQ Option deleted successfully");
      return mcqOptionId;
    } catch (error) {
      toastApiError("Failed to delete MCQ Option", error);
    }
  }
);

export const deleteMcqOptionImage = createAsyncThunk(
  "questionLibrary/deleteMcqOptionImage",
  async (mcqOptionId: UUID) => {
    try {
      await mcqOptionApi.deleteMcqOptionImage(mcqOptionId);
      toastApiSuccess("MCQ Option image deleted successfully");
      return mcqOptionId;
    } catch (error) {
      toastApiError("Failed to delete MCQ Option image", error);
    }
  }
);
