import { createSlice } from "@reduxjs/toolkit";
import { AuthState } from "../types";
import { getCurrentUserThunk, loginThunk, logoutThunk, signUpThunk } from "./authThunks";

const initialState: AuthState = {
  isLoading: false,
  status: false,
  authData: null,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Handle login
      .addCase(loginThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(loginThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        state.status = true;
        state.authData = action.payload ?? null;
      })
      .addCase(loginThunk.rejected, (state, _) => {
        state.isLoading = false;
        state.status = false;
      })
      // Handle logout
      .addCase(logoutThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(logoutThunk.fulfilled, (state) => {
        state.isLoading = false;
        state.status = false;
        state.authData = null;
      })
      .addCase(logoutThunk.rejected, (state) => {
        state.isLoading = false;
      })
      // get current user
      .addCase(getCurrentUserThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getCurrentUserThunk.fulfilled, (state, action) => {
        state.isLoading = false;
        state.status = action.payload ? true : false;
        state.authData = action.payload ?? null;
      })
      .addCase(getCurrentUserThunk.rejected, (state) => {
        state.isLoading = false;
        state.status = false;
      })
      // sign up
      .addCase(signUpThunk.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(signUpThunk.fulfilled, (state, _) => {
        state.isLoading = false;
      })
      .addCase(signUpThunk.rejected, (state, _) => {
        state.isLoading = false;
      });
  },
});

export default authSlice.reducer;
