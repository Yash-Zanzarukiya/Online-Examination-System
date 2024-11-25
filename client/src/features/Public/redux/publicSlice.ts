import { createSlice } from "@reduxjs/toolkit";
import { PublicState } from "../types";
import { checkHealthThunk } from "./publicThunks";

const initialState: PublicState = {
  status: false,
  isLoading: false,
};

export const publicSlice = createSlice({
  name: "public",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(checkHealthThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(checkHealthThunk.fulfilled, (state, _) => {
        state.isLoading = false;
        state.status = true;
      })
      .addCase(checkHealthThunk.rejected, (state, _) => {
        state.isLoading = false;
        state.status = false;
      });
  },
});

export default publicSlice.reducer;
