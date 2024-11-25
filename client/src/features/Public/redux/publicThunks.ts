import { createAsyncThunk } from "@reduxjs/toolkit";
import { checkHealth } from "../api/publicApi";
import { toastApiError, toastApiSuccess } from "@/utils";

export const checkHealthThunk = createAsyncThunk("public/healthCheck", async () => {
  try {
    const apiRes = await checkHealth();
    toastApiSuccess("Server is healthy 🙂", apiRes);
    return apiRes.success;
  } catch (error: any) {
    toastApiError("Oops! Our Server is Sick... 🤒", error);
  }
});
