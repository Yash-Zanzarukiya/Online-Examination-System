import { QuestionFilter } from "@/features/QuestionBuilder/types";
import { createAsyncThunk } from "@reduxjs/toolkit";
import questionPickerApi from "../api/questionPickerApi";
import { toastApiError, toastApiSuccess } from "@/utils";
import { examQuestionsForm } from "../types";
import { UUID } from "crypto";

export const addExamQuestions = createAsyncThunk(
  "questionPicker/addExamQuestions",
  async (payload: examQuestionsForm) => {
    try {
      const apiRes = await questionPickerApi.addExamQuestions(payload);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to add Question...", error);
    }
  }
);

export const removeExamQuestion = createAsyncThunk(
  "questionPicker/removeExamQuestion",
  async (payload: UUID[]) => {
    try {
      const apiRes = await questionPickerApi.removeExamQuestion(payload);
      toastApiSuccess("Success 🙂", apiRes);
      return payload;
    } catch (error: any) {
      toastApiError("Failed to remove Question...", error);
    }
  }
);

export const fetchExamQuestions = createAsyncThunk(
  "questionPicker/fetchExamQuestions",
  async (examId: UUID) => {
    try {
      const apiRes = await questionPickerApi.getExamQuestions(examId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to fetch Exam Questions...", error);
    }
  }
);

export const fetchAllQuestions = createAsyncThunk(
  "questionPicker/fetchAllQuestions",
  async (filters: QuestionFilter) => {
    try {
      const apiRes = await questionPickerApi.getAllQuestions(filters);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to fetch All Questions...", error);
    }
  }
);
