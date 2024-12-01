import { createSlice } from "@reduxjs/toolkit";
import { ExamQuestion, QuestionPickerState } from "../types";
import { UUID } from "crypto";
import {
  fetchAllQuestions,
  fetchExamQuestions,
  addExamQuestions,
  removeExamQuestion,
} from "./questionPickerThunks";
import { Question } from "@/features/QuestionBuilder/types";

const initialState: QuestionPickerState = {
  examId: undefined,
  initialExamQuestions: [],
  examQuestions: [],
  allQuestions: [],
  isLoading: false,
};

const questionPickerSlice = createSlice({
  name: "questionPicker",
  initialState,
  reducers: {
    setExamId(state, action: { payload: UUID }) {
      state.examId = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch All Questions
      .addCase(fetchAllQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(fetchAllQuestions.fulfilled, (state, action) => {
        state.allQuestions = action.payload ?? [];
        state.isLoading = false;
      })
      .addCase(fetchAllQuestions.rejected, (state) => {
        state.isLoading = false;
      })
      // Fetch Exam Questions
      .addCase(fetchExamQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(fetchExamQuestions.fulfilled, (state, action) => {
        if (!action.payload) return;

        const initialExamQuestions: Question[] = [];
        const examQuestions: ExamQuestion[] = [];
        action.payload.forEach((eq) => {
          initialExamQuestions.push(eq.question);
          examQuestions.push({
            id: eq.id,
            questionId: eq.question.id,
          });
        });

        state.initialExamQuestions = initialExamQuestions;
        state.examQuestions = examQuestions;
        state.isLoading = false;
      })
      .addCase(fetchExamQuestions.rejected, (state) => {
        state.isLoading = false;
      })
      // Add Exam Questions
      .addCase(addExamQuestions.pending, () => {})
      .addCase(addExamQuestions.fulfilled, (state, action) => {
        const examQuestions = action.payload ?? [];
        state.examQuestions = [...state.examQuestions, ...examQuestions];
        state.isLoading = false;
      })
      .addCase(addExamQuestions.rejected, (state) => {
        state.isLoading = false;
      })
      // Remove Exam Question
      .addCase(removeExamQuestion.pending, () => {})
      .addCase(removeExamQuestion.fulfilled, (state, action) => {
        if (!action.payload) return;
        state.examQuestions = state.examQuestions.filter(
          (examQuestion) => !action.payload?.includes(examQuestion.id)
        );
        state.isLoading = false;
      })
      .addCase(removeExamQuestion.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export const { setExamId } = questionPickerSlice.actions;

export default questionPickerSlice.reducer;
