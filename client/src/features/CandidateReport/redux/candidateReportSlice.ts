import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { CandidateReportState } from "../types";
import { CandidateState } from "@/features/ExamCandidates/types";
import {
  getCandidateQuestionsAnalysis,
  getCandidateScoreDistribution,
  getCandidateState,
  updateCandidateStatus,
  getExamActivities,
} from "./candidateReportThunks";

const initialState: CandidateReportState = {
  candidateState: null,
  questionsAnalysis: [],
  scoreDistribution: null,
  examActivities: [],
  isLoading: false,
};

const candidateReportSlice = createSlice({
  name: "candidateReport",
  initialState,
  reducers: {
    setCandidateState: (state, action: PayloadAction<CandidateState>) => {
      state.candidateState = action.payload;
    },
    resetState: () => initialState,
  },
  extraReducers: (builder) => {
    builder
      // Candidate State
      .addCase(getCandidateState.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getCandidateState.fulfilled, (state, action) => {
        if (action.payload) state.candidateState = action.payload;
        state.isLoading = false;
      })
      .addCase(getCandidateState.rejected, (state) => {
        state.isLoading = false;
      })

      // Score Distribution
      .addCase(getCandidateScoreDistribution.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getCandidateScoreDistribution.fulfilled, (state, action) => {
        if (action.payload) state.scoreDistribution = action.payload;
        state.isLoading = false;
      })
      .addCase(getCandidateScoreDistribution.rejected, (state) => {
        state.isLoading = false;
      })

      // Questions Analysis
      .addCase(getCandidateQuestionsAnalysis.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getCandidateQuestionsAnalysis.fulfilled, (state, action) => {
        if (action.payload) state.questionsAnalysis = action.payload;
        state.isLoading = false;
      })
      .addCase(getCandidateQuestionsAnalysis.rejected, (state) => {
        state.isLoading = false;
      })

      // update status
      .addCase(updateCandidateStatus.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateCandidateStatus.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload && state.candidateState) {
          state.candidateState.status = action.payload;
        }
      })
      .addCase(updateCandidateStatus.rejected, (state) => {
        state.isLoading = false;
      });

    // Exam Activities
    builder
      .addCase(getExamActivities.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getExamActivities.fulfilled, (state, action) => {
        if (action.payload) state.examActivities = action.payload;
        state.isLoading = false;
      })
      .addCase(getExamActivities.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export const { setCandidateState, resetState } = candidateReportSlice.actions;

export default candidateReportSlice.reducer;
