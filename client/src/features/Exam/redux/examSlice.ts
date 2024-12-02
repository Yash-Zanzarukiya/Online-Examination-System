import { createSlice } from "@reduxjs/toolkit";
import { ExamsState } from "../types";
import {
  createExam,
  deleteExam,
  getAllExams,
  getExamById,
  updateExam,
  updateExamSchedule,
} from "./examThunks";

const initialState: ExamsState = {
  exams: [],
  exam: null,
  isLoading: false,
};

const examSlice = createSlice({
  name: "exam",
  initialState,
  reducers: {
    setExam: (state, action) => {
      state.exam = action.payload;
    },
  },
  extraReducers: (builder) => {
    // Create Exam
    builder.addCase(createExam.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(createExam.fulfilled, (state, action) => {
      if (action.payload) state.exams = [...state.exams, action.payload];
      state.isLoading = false;
    });
    builder.addCase(createExam.rejected, (state) => {
      state.isLoading = false;
    });
    // Get All Exams
    builder.addCase(getAllExams.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllExams.fulfilled, (state, action) => {
      if (action.payload) state.exams = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getAllExams.rejected, (state) => {
      state.isLoading = false;
    });
    // Get Exam By Id
    builder.addCase(getExamById.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getExamById.fulfilled, (state, action) => {
      if (action.payload) state.exam = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getExamById.rejected, (state) => {
      state.isLoading = false;
    });
    // Update Exam
    builder.addCase(updateExam.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(updateExam.fulfilled, (state, action) => {
      if (action.payload) state.exam = action.payload;
      state.isLoading = false;
    });
    builder.addCase(updateExam.rejected, (state) => {
      state.isLoading = false;
    });
    // Update Exam Schedule
    builder.addCase(updateExamSchedule.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(updateExamSchedule.fulfilled, (state, action) => {
      if (action.payload) {
        state.exam = action.payload;
        state.exams = state.exams.map((exam) => {
          if (exam.id === action.payload?.id) return action.payload;
          return exam;
        });
      }
      state.isLoading = false;
    });
    builder.addCase(updateExamSchedule.rejected, (state) => {
      state.isLoading = false;
    });
    // Delete Exam
    builder.addCase(deleteExam.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteExam.fulfilled, (state, action) => {
      state.exams = state.exams.filter((exam) => exam.id !== action.payload);
      state.isLoading = false;
    });
    builder.addCase(deleteExam.rejected, (state) => {
      state.isLoading = false;
    });
  },
});

export const { setExam } = examSlice.actions;

export default examSlice.reducer;
