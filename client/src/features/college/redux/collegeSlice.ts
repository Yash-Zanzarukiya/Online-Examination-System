import { createSlice } from "@reduxjs/toolkit";
import { CollegeState } from "../types";
import {
  createCollegeThunk,
  deleteCollegeThunk,
  editCollegeThunk,
  getAllCollegesThunk,
  getCollegeByIdThunk,
} from "./collegeThunks";

const initialState: CollegeState = {
  isLoading: false,
  colleges: [],
};

export const collegeSlice = createSlice({
  name: "college",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    // Fetch all colleges
    builder.addCase(getAllCollegesThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getAllCollegesThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      state.colleges = action.payload ?? [];
    });
    builder.addCase(getAllCollegesThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Fetch college by id
    builder.addCase(getCollegeByIdThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(getCollegeByIdThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload)
        state.colleges = state.colleges.map((college) =>
          college.id === action.payload?.id ? action.payload : college
        );
    });
    builder.addCase(getCollegeByIdThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Create college
    builder.addCase(createCollegeThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(createCollegeThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      if (action.payload) state.colleges = [...state.colleges, action.payload];
    });
    builder.addCase(createCollegeThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Edit college
    builder.addCase(editCollegeThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(editCollegeThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      state.colleges = state.colleges.map((college) =>
        college.id === action.payload?.id ? action.payload : college
      );
    });
    builder.addCase(editCollegeThunk.rejected, (state) => {
      state.isLoading = false;
    });

    // Delete college
    builder.addCase(deleteCollegeThunk.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(deleteCollegeThunk.fulfilled, (state, action) => {
      state.isLoading = false;
      state.colleges = state.colleges.filter((college) => college.id !== action.payload);
    });
    builder.addCase(deleteCollegeThunk.rejected, (state) => {
      state.isLoading = false;
    });
  },
});

export default collegeSlice.reducer;
