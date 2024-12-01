import { createAsyncThunk } from "@reduxjs/toolkit";
import { toastApiError, toastApiSuccess } from "@/utils";
import { ExamForm } from "../types";
import { UUID } from "crypto";
import examApi from "../api/ExamApi";

export const createExam = createAsyncThunk("exam/create", async (examData: ExamForm) => {
  try {
    const apiRes = await examApi.createExam(examData);
    toastApiSuccess("Success 🙂", apiRes);
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Create Exam", error);
  }
});

export const getAllExams = createAsyncThunk("exam/getAll", async () => {
  try {
    const apiRes = await examApi.getAllExams();
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Fetch Exams", error);
  }
});

export const getExamById = createAsyncThunk("exam/getById", async (examId: UUID) => {
  try {
    const apiRes = await examApi.getExamById(examId);
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Fetch Exam", error);
  }
});

export const updateExam = createAsyncThunk(
  "exam/update",
  async ({ examId, examData }: { examId: UUID; examData: ExamForm }) => {
    try {
      const apiRes = await examApi.updateExam(examId, examData);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Update Exam", error);
    }
  }
);

export const deleteExam = createAsyncThunk("exam/delete", async (examId: UUID) => {
  try {
    const apiRes = await examApi.deleteExam(examId);
    toastApiSuccess("Success 🙂", apiRes);
    return examId;
  } catch (error) {
    toastApiError("Failed to Delete Exam", error);
  }
});
