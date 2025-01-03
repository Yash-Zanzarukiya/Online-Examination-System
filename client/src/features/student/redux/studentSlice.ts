import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { StudentData, StudentProfileState } from "../types";
import {
  createStudentProfileThunk,
  deleteStudentProfileThunk,
  getStudentProfileThunk,
  updateStudentProfileThunk,
  getAllStudentDataThunk,
  uploadStudentDataThunk,
} from "./studentThunks";

const initialState: StudentProfileState = {
  students: [],
  profile: null,
  isLoading: false,
};

const studentProfileSlice = createSlice({
  name: "studentProfile",
  initialState,
  reducers: {
    setStudentProfile: (state, action: PayloadAction<StudentData>) => {
      const { studentProfileId: id } = action.payload;

      state.profile = {
        ...action.payload,
        id,
        createdAt: null,
        updatedAt: null,
      };
    },
    clearStudentProfile: (state) => {
      state.profile = null;
    },
  },
  extraReducers: (builder) => {
    // Create Student Profile
    builder.addCase(createStudentProfileThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(createStudentProfileThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.profile = action.payload;
    });
    builder.addCase(createStudentProfileThunk.rejected, (state) => {
      state.isLoading = false;
    });
    // Update Student Profile
    builder.addCase(updateStudentProfileThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(updateStudentProfileThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.profile = action.payload;
    });
    builder.addCase(updateStudentProfileThunk.rejected, (state) => {
      state.isLoading = false;
    });
    // Get Student Profile
    builder.addCase(getStudentProfileThunk.pending, (state) => {
      state.isLoading = true;
      state.profile = null;
    });
    builder.addCase(getStudentProfileThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.profile = action.payload;
    });
    builder.addCase(getStudentProfileThunk.rejected, (state) => {
      state.isLoading = false;
    });
    // Delete Student Profile
    builder.addCase(deleteStudentProfileThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteStudentProfileThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      state.profile = null;
      state.students = state.students.filter(
        (student) => student.studentProfileId !== action.payload
      );
    });
    builder.addCase(deleteStudentProfileThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Upload Student Data
    builder.addCase(uploadStudentDataThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(uploadStudentDataThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.students = [...action.payload, ...state.students];
    });
    builder.addCase(uploadStudentDataThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Get All Student Data
    builder.addCase(getAllStudentDataThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllStudentDataThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.students = action.payload;
    });
    builder.addCase(getAllStudentDataThunk.rejected, (state) => {
      state.isLoading = false;
    });
  },
});

export const { setStudentProfile, clearStudentProfile } = studentProfileSlice.actions;

export default studentProfileSlice.reducer;
