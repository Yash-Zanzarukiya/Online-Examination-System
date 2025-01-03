import { QuestionFilter } from "@/features/QuestionBuilder/types";
import { createAsyncThunk } from "@reduxjs/toolkit";
import examQuestionsApi from "../api/examQuestionsApi";
import { toastApiError, toastApiSuccess } from "@/utils";
import { examQuestionsForm } from "../types";
import { UUID } from "crypto";

export const addExamQuestions = createAsyncThunk(
  "questionPicker/addExamQuestions",
  async (payload: examQuestionsForm) => {
    try {
      const apiRes = await examQuestionsApi.addExamQuestions(payload);
      toastApiSuccess("Question Added Successfully 🙂");
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
      await examQuestionsApi.removeExamQuestion(payload);
      toastApiSuccess("Question Removed Successfully 🙂");
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
      const apiRes = await examQuestionsApi.getExamQuestions(examId);
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
      const apiRes = await examQuestionsApi.getAllQuestions(filters);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to fetch All Questions...", error);
    }
  }
);
