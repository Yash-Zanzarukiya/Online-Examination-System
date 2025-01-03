import { createAsyncThunk } from "@reduxjs/toolkit";
import { toastApiError, toastApiSuccess } from "@/utils";
import { getCurrentUser, logout, SignIn, SignUp } from "../api/authApi";
import { signInSchema } from "../validators";
import { z } from "zod";
import { ISignUpDTO } from "../types";

export const loginThunk = createAsyncThunk(
  "auth/login",
  async (loginDTO: z.infer<typeof signInSchema>) => {
    try {
      const apiRes = await SignIn(loginDTO);
      toastApiSuccess("Login Successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Login Failed...", error);
    }
  }
);

export const logoutThunk = createAsyncThunk("auth/logout", async () => {
  try {
    const apiRes = await logout();
    toastApiSuccess("Logout Successfully 🙂", apiRes);
    return apiRes.data;
  } catch (error: any) {
    toastApiError("Logout Failed...", error);
  }
});

export const getCurrentUserThunk = createAsyncThunk("auth/me", async () => {
  try {
    const apiRes = await getCurrentUser();
    return apiRes.data.data;
  } catch (error: any) {
    // toastApiError("Not Logged In...", error);
  }
});

export const signUpThunk = createAsyncThunk("auth/register", async (signUpDTO: ISignUpDTO) => {
  try {
    const apiRes = await SignUp(signUpDTO);
    toastApiSuccess("User Registered Successfully 🙂", apiRes);
    return apiRes.data;
  } catch (error: any) {
    toastApiError("Registration Failed...", error);
  }
});
