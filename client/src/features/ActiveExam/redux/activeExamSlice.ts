import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import {
  ActiveExamQuestionsResponse,
  ActiveExamState,
  ConnectionResponse,
  QuestionAttemptStatus,
} from "../types";
import { UUID } from "crypto";

const initialState: ActiveExamState = {
  sessionType: null,
  activeExamData: null,
  examQuestions: [],
  examQuestionsState: {},
  questionStartTimes: {},
  isFetchingQuestions: false,
  currentQuestionIndex: 0,
  timeRemaining: 0,
  isExamStarted: false,
  isExamSubmitted: false,
  isNewLoginDetected: false,
};

const activeExamSlice = createSlice({
  name: "exam",
  initialState,
  reducers: {
    startExam: (state) => {
      state.isExamStarted = true;
      const currentQuestionId = state.examQuestions[0]?.question.id;
      if (!state.examQuestionsState[currentQuestionId]) {
        state.examQuestionsState[currentQuestionId] = {
          questionId: currentQuestionId,
          status: QuestionAttemptStatus.VISITED,
          selectedOptionId: null,
          submittedCode: null,
        };
        state.questionStartTimes[currentQuestionId] = Date.now();
      }
    },
    submitExam: (state) => {
      state.isExamSubmitted = true;
    },
    setAnswer: (state, action: PayloadAction<{ questionId: UUID; selectedOptionId: UUID }>) => {
      const { questionId, selectedOptionId } = action.payload;
      state.examQuestionsState[questionId] = {
        ...state.examQuestionsState[questionId],
        questionId,
        selectedOptionId,
        status: QuestionAttemptStatus.ANSWERED,
      };
    },
    setCode: (state, action: PayloadAction<{ questionId: UUID; code: string }>) => {
      const { questionId, code } = action.payload;
      state.examQuestionsState[questionId] = {
        ...state.examQuestionsState[questionId],
        questionId,
        submittedCode: code,
        status: QuestionAttemptStatus.ANSWERED,
      };
    },
    navigateQuestion: (state, action: PayloadAction<number>) => {
      // this logic is tightly coupled with action dispatcher function

      const newIndex = action.payload;
      const newQuestionId = state.examQuestions[newIndex].question.id;

      state.currentQuestionIndex = newIndex;

      if (!state.examQuestionsState[newQuestionId]) {
        state.examQuestionsState[newQuestionId] = {
          questionId: newQuestionId,
          status: QuestionAttemptStatus.VISITED,
          selectedOptionId: null,
          submittedCode: null,
        };
      } else if (
        state.examQuestionsState[newQuestionId].status === QuestionAttemptStatus.NOT_VISITED
      ) {
        state.examQuestionsState[newQuestionId].status = QuestionAttemptStatus.VISITED;
      }

      state.questionStartTimes[newQuestionId] = Date.now();
    },
    decrementTime: (state) => {
      state.timeRemaining -= 1;
    },
    saveConnectionResponse: (state, action: PayloadAction<ConnectionResponse>) => {
      state.sessionType = action.payload.sessionType;
      state.activeExamData = action.payload.activeExamData;
      state.timeRemaining = action.payload.timeRemaining;
    },
    setExamQuestions: (state, action: PayloadAction<ActiveExamQuestionsResponse>) => {
      state.examQuestions = action.payload.questions;
      state.examQuestionsState = action.payload.questionsState;
      state.isFetchingQuestions = false;
    },
    setFetchingQuestions: (state, action: PayloadAction<boolean>) => {
      state.isFetchingQuestions = action.payload;
    },
    setNewLoginDetected: (state, action: PayloadAction<boolean>) => {
      state.isNewLoginDetected = action.payload;
    },
  },
});

export const {
  startExam,
  setAnswer,
  setCode,
  navigateQuestion,
  decrementTime,
  submitExam,
  saveConnectionResponse,
  setExamQuestions,
  setFetchingQuestions,
  setNewLoginDetected,
} = activeExamSlice.actions;

export default activeExamSlice.reducer;
