import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Exam, ExamsState, ScheduledExam } from "../types";
import {
  createExam,
  deleteExam,
  getAllExams,
  getExamById,
  updateExam,
  scheduleExam,
  getExamScheduleById,
  getScheduledExams,
  updateExamSchedule,
  updateScheduleExamStatus,
  deleteExamSchedule,
} from "./examThunks";

const initialState: ExamsState = {
  exams: [],
  exam: null,
  scheduledExams: [],
  scheduledExam: null,
  isLoading: false,
};

const examSlice = createSlice({
  name: "exam",
  initialState,
  reducers: {
    setExam: (state, action: PayloadAction<Exam>) => {
      state.exam = action.payload;
    },
    setScheduledExam: (state, action: PayloadAction<ScheduledExam>) => {
      state.scheduledExam = action.payload;
    },
    resetExam: (state) => {
      state.exam = null;
    },
    resetScheduledExam: (state) => {
      state.scheduledExam = null;
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
      state.exams = [];
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
    // Schedule Exam
    builder.addCase(scheduleExam.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(scheduleExam.fulfilled, (state, action) => {
      if (action.payload) state.scheduledExams = [...state.scheduledExams, action.payload];
      state.isLoading = false;
    });
    builder.addCase(scheduleExam.rejected, (state) => {
      state.isLoading = false;
    });
    // Get Exam Schedule By Id
    builder.addCase(getExamScheduleById.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getExamScheduleById.fulfilled, (state, action) => {
      if (action.payload) state.scheduledExam = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getExamScheduleById.rejected, (state) => {
      state.isLoading = false;
    });
    // Get Scheduled Exams
    builder.addCase(getScheduledExams.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getScheduledExams.fulfilled, (state, action) => {
      if (action.payload) state.scheduledExams = action.payload;
      state.isLoading = false;
    });
    builder.addCase(getScheduledExams.rejected, (state) => {
      state.isLoading = false;
    });
    // Update Exam Schedule
    builder.addCase(updateExamSchedule.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(updateExamSchedule.fulfilled, (state, action) => {
      if (action.payload) state.scheduledExam = action.payload;
      state.isLoading = false;
    });
    builder.addCase(updateExamSchedule.rejected, (state) => {
      state.isLoading = false;
    });
    // Update Schedule Exam Status
    builder.addCase(updateScheduleExamStatus.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(updateScheduleExamStatus.fulfilled, (state, action) => {
      if (action.payload) state.scheduledExam = action.payload;
      state.isLoading = false;
    });
    builder.addCase(updateScheduleExamStatus.rejected, (state) => {
      state.isLoading = false;
    });
    // Delete Exam Schedule
    builder.addCase(deleteExamSchedule.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteExamSchedule.fulfilled, (state, action) => {
      state.scheduledExams = state.scheduledExams.filter((exam) => exam.id !== action.payload);
      state.isLoading = false;
    });
    builder.addCase(deleteExamSchedule.rejected, (state) => {
      state.isLoading = false;
    });
  },
});

export const { setExam, resetExam, resetScheduledExam, setScheduledExam } = examSlice.actions;

export default examSlice.reducer;
