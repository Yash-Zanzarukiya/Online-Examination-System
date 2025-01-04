import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ExamAttemptStatus, ExamCandidatesState } from "../types";
import { fetchCandidates } from "./examCandidatesThunks";
import { UUID } from "crypto";

const initialState: ExamCandidatesState = {
  candidates: [],
  isLoading: false,
};

const examCandidatesSlice = createSlice({
  name: "examCandidates",
  initialState,
  reducers: {
    updateExamCandidateStatus: (
      state,
      action: PayloadAction<{ examAttemptId: UUID; status: ExamAttemptStatus }>
    ) => {
      const { examAttemptId, status } = action.payload;
      state.candidates = state.candidates.map((candidate) => {
        if (candidate.examAttemptId === examAttemptId) candidate.status = status;
        return candidate;
      });
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch Candidates
      .addCase(fetchCandidates.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(fetchCandidates.fulfilled, (state, action) => {
        if (action.payload) state.candidates = action.payload;
        state.isLoading = false;
      })
      .addCase(fetchCandidates.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export const { updateExamCandidateStatus } = examCandidatesSlice.actions;

export default examCandidatesSlice.reducer;
