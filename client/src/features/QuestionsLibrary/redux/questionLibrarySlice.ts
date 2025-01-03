import { createSlice } from "@reduxjs/toolkit";
import { QuestionLibraryState } from "../types";
import { getAllQuestions, uploadQuestions, deleteQuestion, updateQuestion } from "./questionThunks";
import { getMcqQuestionById, createMcqQuestion, createBulkMcqQuestions } from "./mcqQuestionThunks";
import {
  createProgrammingQuestion,
  createBulkProgrammingQuestions,
  getProgrammingQuestionById,
  getProgrammingQuestionByQuestionId,
  updateProgrammingQuestion,
  deleteProgrammingQuestion,
} from "./programmingQuestionThunks";

const initialState: QuestionLibraryState = {
  questions: [],
  mcqQuestion: null,
  programmingQuestion: null,
  isLoading: false,
};

const questionLibrarySlice = createSlice({
  name: "questionLibrary",
  initialState,
  reducers: {
    resetMcqQuestion: (state) => {
      state.mcqQuestion = null;
    },
    resetProgrammingQuestion: (state) => {
      state.programmingQuestion = null;
    },
  },
  extraReducers: (builder) => {
    // Get All Questions
    builder
      .addCase(getAllQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getAllQuestions.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) state.questions = action.payload;
      })
      .addCase(getAllQuestions.rejected, (state) => {
        state.isLoading = false;
      });
    // Upload Questions
    builder
      .addCase(uploadQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(uploadQuestions.fulfilled, (state, action) => {
        state.isLoading = false;
        if (action.payload) state.questions = [...action.payload, ...state.questions];
      })
      .addCase(uploadQuestions.rejected, (state) => {
        state.isLoading = false;
      });
    // Delete Question
    builder
      .addCase(deleteQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteQuestion.fulfilled, (state, action) => {
        const deletedQuestionId = action.payload;
        state.questions = state.questions.filter((q) => q.id !== deletedQuestionId);
        state.mcqQuestion = null;
        state.programmingQuestion = null;
        state.isLoading = false;
      })
      .addCase(deleteQuestion.rejected, (state) => {
        state.isLoading = false;
      });

    // update Question
    builder
      .addCase(updateQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateQuestion.fulfilled, (state, action) => {
        state.isLoading = false;
        const updatedQuestion = action.payload;
        if (updatedQuestion) {
          state.questions = state.questions.map((q) =>
            q.id === updatedQuestion.id ? updatedQuestion : q
          );
          if (state.mcqQuestion) state.mcqQuestion.question = updatedQuestion;
          else if (state.programmingQuestion) state.programmingQuestion.question = updatedQuestion;
        }
      })
      .addCase(updateQuestion.rejected, (state) => {
        state.isLoading = false;
      });

    // Create Mcq Question
    builder
      .addCase(createMcqQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createMcqQuestion.fulfilled, (state, action) => {
        state.isLoading = false;
        const mcqQuestion = action.payload;
        if (mcqQuestion) {
          state.questions.push(mcqQuestion.question);
          state.mcqQuestion = mcqQuestion;
        }
      })
      .addCase(createMcqQuestion.rejected, (state) => {
        state.isLoading = false;
      });
    // Create Bulk Mcq Questions
    builder
      .addCase(createBulkMcqQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createBulkMcqQuestions.fulfilled, (state, action) => {
        state.isLoading = false;
        const mcqQuestions = action.payload;
        if (mcqQuestions) {
          state.questions.push(...mcqQuestions.map((mcq) => mcq.question));
        }
      })
      .addCase(createBulkMcqQuestions.rejected, (state) => {
        state.isLoading = false;
      });
    // Get Mcq Question By Id
    builder
      .addCase(getMcqQuestionById.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getMcqQuestionById.fulfilled, (state, action) => {
        state.isLoading = false;
        state.mcqQuestion = action.payload || null;
      })
      .addCase(getMcqQuestionById.rejected, (state) => {
        state.isLoading = false;
      });
    // Create Programming Question
    builder
      .addCase(createProgrammingQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createProgrammingQuestion.fulfilled, (state, action) => {
        state.isLoading = false;
        const programmingQuestion = action.payload;
        if (programmingQuestion) {
          state.questions.push(programmingQuestion.question);
        }
      })
      .addCase(createProgrammingQuestion.rejected, (state) => {
        state.isLoading = false;
      });
    // Create Bulk Programming Questions
    builder
      .addCase(createBulkProgrammingQuestions.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createBulkProgrammingQuestions.fulfilled, (state, action) => {
        state.isLoading = false;
        const programmingQuestions = action.payload;
        if (programmingQuestions) {
          state.questions.push(...programmingQuestions.map((p) => p.question));
        }
      })
      .addCase(createBulkProgrammingQuestions.rejected, (state) => {
        state.isLoading = false;
      });
    // Get Programming Question By Id
    builder
      .addCase(getProgrammingQuestionById.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getProgrammingQuestionById.fulfilled, (state, action) => {
        state.isLoading = false;
        state.programmingQuestion = action.payload || null;
      })
      .addCase(getProgrammingQuestionById.rejected, (state) => {
        state.isLoading = false;
      });
    // Get Programming Question By Question Id
    builder
      .addCase(getProgrammingQuestionByQuestionId.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getProgrammingQuestionByQuestionId.fulfilled, (state, action) => {
        state.isLoading = false;
        state.programmingQuestion = action.payload || null;
      })
      .addCase(getProgrammingQuestionByQuestionId.rejected, (state) => {
        state.isLoading = false;
      });
    // Update Programming Question
    builder
      .addCase(updateProgrammingQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(updateProgrammingQuestion.fulfilled, (state, action) => {
        state.isLoading = false;
        const updatedPQ = action.payload;
        if (updatedPQ) {
          state.programmingQuestion = updatedPQ;
          state.questions = state.questions.map((q) =>
            q.id === updatedPQ.question.id ? updatedPQ.question : q
          );
        }
      })
      .addCase(updateProgrammingQuestion.rejected, (state) => {
        state.isLoading = false;
      });
    // Delete Programming Question
    builder
      .addCase(deleteProgrammingQuestion.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteProgrammingQuestion.fulfilled, (state, action) => {
        const deletedQuestionId = action.payload;
        state.questions = state.questions.filter((q) => q.id !== deletedQuestionId);
        state.isLoading = false;
      })
      .addCase(deleteProgrammingQuestion.rejected, (state) => {
        state.isLoading = false;
      });
  },
});

export const { resetMcqQuestion, resetProgrammingQuestion } = questionLibrarySlice.actions;

export default questionLibrarySlice.reducer;
