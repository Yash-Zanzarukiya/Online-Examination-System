import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ActiveExamState } from "../types";
import { getQuestionsForExam } from "./activeExamThunks";

const initialState: ActiveExamState = {
  examQuestions: [],
  isFetchingQuestions: false,
  currentQuestionIndex: 0,
  answers: {},
  questionStates: {},
  timeRemaining: 60 * 60,
  isInstructionsAgreed: false,
  isExamStarted: false,
  isExamSubmitted: false,
};

const activeExamSlice = createSlice({
  name: "exam",
  initialState,
  reducers: {
    startExam: (state) => {
      state.isExamStarted = true;
      state.questionStates = {};
    },
    setAnswer: (state, action: PayloadAction<{ questionId: string; answerId: string }>) => {
      const { questionId, answerId } = action.payload;
      state.answers[questionId] = answerId;
      state.questionStates[questionId] = "answered";
    },
    navigateQuestion: (state, action: PayloadAction<number>) => {
      state.currentQuestionIndex = action.payload;
    },
    decrementTime: (state) => {
      state.timeRemaining -= 1;
    },
    submitExam: (state) => {
      state.isExamSubmitted = true;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getQuestionsForExam.pending, (state, _) => {
      state.isFetchingQuestions = true;
    });
    builder.addCase(getQuestionsForExam.fulfilled, (state, action) => {
      state.isFetchingQuestions = false;
      if (action.payload) state.examQuestions = action.payload;
    });
    builder.addCase(getQuestionsForExam.rejected, (state, _) => {
      state.isFetchingQuestions = false;
    });
  },
});

export const { startExam, setAnswer, navigateQuestion, decrementTime, submitExam } =
  activeExamSlice.actions;

export default activeExamSlice.reducer;
