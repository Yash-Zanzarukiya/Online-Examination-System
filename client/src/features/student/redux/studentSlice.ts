import { createSlice } from "@reduxjs/toolkit";
import { StudentProfileState } from "../types";
import {
  createStudentProfileThunk,
  deleteStudentProfileThunk,
  getStudentProfileThunk,
  updateStudentProfileThunk,
} from "./studentThunks";

const initialState: StudentProfileState = {
  isLoading: false,
  profile: null,
};

const studentProfileSlice = createSlice({
  name: "studentProfile",
  initialState,
  reducers: {},
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
    builder.addCase(deleteStudentProfileThunk.fulfilled, (state) => {
      state.isLoading = false;
      state.profile = null;
    });
    builder.addCase(deleteStudentProfileThunk.rejected, (state) => {
      state.isLoading = false;
    });
  },
});

export default studentProfileSlice.reducer;
