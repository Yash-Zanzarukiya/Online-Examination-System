import { createSlice } from "@reduxjs/toolkit";
import {
  createFullQuestionThunk,
  createBulkFullQuestionThunk,
  createQuestionThunk,
  createBulkQuestionsThunk,
  getQuestionByIdThunk,
  getAllQuestionsThunk,
  getAllFullQuestionsThunk,
  getFullQuestionByIdThunk,
  updateQuestionThunk,
  deleteQuestionThunk,
} from "./questionThunks";
import {
  createMcqOptionThunk,
  createBulkMcqOptionsThunk,
  deleteOptionsByQuestionIdThunk,
  getOptionsByQuestionIdThunk,
  getMcqOptionByIdThunk,
  updateMcqOptionThunk,
  deleteMcqOptionThunk,
  deleteMcqOptionImageThunk,
} from "./optionThunks";
import { QuestionState } from "../types";

const initialState: QuestionState = {
  questions: [],
  question: {},
  isLoading: false,
};

// FIXME : fix all cases
const questionSlice = createSlice({
  name: "questions",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Create full question
      .addCase(createFullQuestionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createFullQuestionThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.questions = [...state.questions, action.payload];
        }
      })
      .addCase(createFullQuestionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Create bulk full questions
      .addCase(createBulkFullQuestionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createBulkFullQuestionThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.questions = [...state.questions, ...action.payload];
        }
      })
      .addCase(createBulkFullQuestionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Create single question
      .addCase(createQuestionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createQuestionThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.questions = [...state.questions, action.payload];
        }
      })
      .addCase(createQuestionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Create bulk questions
      .addCase(createBulkQuestionsThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createBulkQuestionsThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.questions = [...state.questions, ...action.payload];
        }
      })
      .addCase(createBulkQuestionsThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Get question by ID
      .addCase(getQuestionByIdThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getQuestionByIdThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.question = action.payload;
        }
      })
      .addCase(getQuestionByIdThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Get all questions
      .addCase(getAllQuestionsThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getAllQuestionsThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        // if (action.payload) state.questions = action.payload;
      })
      .addCase(getAllQuestionsThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Get all full questions
      .addCase(getAllFullQuestionsThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getAllFullQuestionsThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) state.questions = action.payload;
      })
      .addCase(getAllFullQuestionsThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Get full question by ID
      .addCase(getFullQuestionByIdThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getFullQuestionByIdThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.question = action.payload;
        }
      })
      .addCase(getFullQuestionByIdThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Update question : done
      .addCase(updateQuestionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateQuestionThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) {
          state.questions = state.questions.map((question) => {
            if (question.question.id === action.payload?.id) {
              question.question = action.payload;
              return question;
            }
            return question;
          });
        }
      })
      .addCase(updateQuestionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // Delete question : done
      .addCase(deleteQuestionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteQuestionThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        state.questions = state.questions.filter(
          (question) => question.question.id !== action.meta.arg
        );
      })
      .addCase(deleteQuestionThunk.rejected, (state) => {
        state.isLoading = false;
      })

      // Handle MCQ option thunks
      .addCase(createMcqOptionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createMcqOptionThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(createMcqOptionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // createBulkMcqOptionsThunk
      .addCase(createBulkMcqOptionsThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createBulkMcqOptionsThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(createBulkMcqOptionsThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // deleteOptionsByQuestionIdThunk
      .addCase(deleteOptionsByQuestionIdThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteOptionsByQuestionIdThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteOptionsByQuestionIdThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // getOptionsByQuestionIdThunk
      .addCase(getOptionsByQuestionIdThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getOptionsByQuestionIdThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(getOptionsByQuestionIdThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // getMcqOptionByIdThunk
      .addCase(getMcqOptionByIdThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getMcqOptionByIdThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(getMcqOptionByIdThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // updateMcqOptionThunk
      .addCase(updateMcqOptionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateMcqOptionThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(updateMcqOptionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // deleteMcqOptionThunk
      .addCase(deleteMcqOptionThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteMcqOptionThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteMcqOptionThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // deleteMcqOptionImageThunk
      .addCase(deleteMcqOptionImageThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteMcqOptionImageThunk.fulfilled, (state) => {
        state.isLoading = false;
      })
      .addCase(deleteMcqOptionImageThunk.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export default questionSlice.reducer;
