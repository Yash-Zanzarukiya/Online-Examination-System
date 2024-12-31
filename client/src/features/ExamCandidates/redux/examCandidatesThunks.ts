import { createAsyncThunk } from "@reduxjs/toolkit";
import { toastApiError, toastApiSuccess } from "@/utils";
import { UUID } from "crypto";
import examCandidatesApi from "../api/examCandidatesApi";

export const fetchCandidates = createAsyncThunk(
  "examCandidates/fetchCandidates",
  async (scheduledExamId: UUID) => {
    try {
      const apiRes = await examCandidatesApi.fetchCandidates(scheduledExamId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch candidates", error);
    }
  }
);
