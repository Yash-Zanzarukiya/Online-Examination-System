import { createAsyncThunk } from "@reduxjs/toolkit";
import { McqOptionForm } from "../types";
import mcqOptionApi from "../api/optionApi";
import { toastApiSuccess, toastApiError } from "@/utils";
import { UUID } from "crypto";

const {
  createMcqOptionApi,
  createMultipleMcqOptionsApi,
  getOptionsByQuestionIdApi,
  getMcqOptionByIdApi,
  updateMcqOptionApi,
  deleteMcqOptionApi,
  deleteOptionsByQuestionIdApi,
  deleteMcqOptionImageApi,
} = mcqOptionApi;

export const createMcqOptionThunk = createAsyncThunk(
  "mcqOptions/create",
  async (payload: McqOptionForm) => {
    try {
      const apiRes = await createMcqOptionApi(payload);
      toastApiSuccess("MCQ Option Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create MCQ Option...", error);
    }
  }
);

export const createBulkMcqOptionsThunk = createAsyncThunk(
  "mcqOptions/createMultiple",
  async (payload: McqOptionForm[]) => {
    try {
      const apiRes = await createMultipleMcqOptionsApi(payload);
      toastApiSuccess("All MCQ Options Created Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Create Multiple MCQ Options...", error);
    }
  }
);

export const getOptionsByQuestionIdThunk = createAsyncThunk(
  "mcqOptions/getByQuestionId",
  async (questionId: UUID) => {
    try {
      const apiRes = await getOptionsByQuestionIdApi(questionId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch MCQ Options...", error);
    }
  }
);

export const getMcqOptionByIdThunk = createAsyncThunk(
  "mcqOptions/getById",
  async (optionId: UUID) => {
    try {
      const apiRes = await getMcqOptionByIdApi(optionId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Fetch MCQ Option...", error);
    }
  }
);

export const updateMcqOptionThunk = createAsyncThunk(
  "mcqOptions/update",
  async ({ optionId, optionData }: { optionId: UUID; optionData: McqOptionForm }) => {
    try {
      const apiRes = await updateMcqOptionApi(optionId, optionData);
      toastApiSuccess("MCQ Option Updated Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Update MCQ Option...", error);
    }
  }
);

export const deleteMcqOptionThunk = createAsyncThunk(
  "mcqOptions/delete",
  async (optionId: UUID) => {
    try {
      const apiRes = await deleteMcqOptionApi(optionId);
      toastApiSuccess("MCQ Option Deleted Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Delete MCQ Option...", error);
    }
  }
);

export const deleteOptionsByQuestionIdThunk = createAsyncThunk(
  "mcqOptions/deleteByQuestionId",
  async (questionId: UUID) => {
    try {
      const apiRes = await deleteOptionsByQuestionIdApi(questionId);
      toastApiSuccess("MCQ Options Deleted Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to Delete MCQ Options...", error);
    }
  }
);

export const deleteMcqOptionImageThunk = createAsyncThunk(
  "mcqOptions/deleteImage",
  async (optionId: UUID) => {
    try {
      const apiRes = await deleteMcqOptionImageApi(optionId);
      toastApiSuccess("MCQ Option Image Deleted Successfully 🙂", apiRes);
    } catch (error: any) {
      toastApiError("Failed to Delete MCQ Option Image...", error);
    }
  }
);
