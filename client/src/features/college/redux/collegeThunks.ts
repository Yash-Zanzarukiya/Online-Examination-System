import { createAsyncThunk } from "@reduxjs/toolkit";
import {
  addCollege,
  deleteCollege,
  getCollegeById,
  getColleges,
  updateCollege,
} from "../api/collegeApi";
import { toastApiError, toastApiSuccess } from "@/utils";
import { CollegeDTO } from "../types";
import { UUID } from "crypto";

export const getAllCollegesThunk = createAsyncThunk("college/getAllColleges", async () => {
  try {
    const apiRes = await getColleges();
    return apiRes.data.data;
  } catch (error: any) {
    toastApiError("Failed to fetch colleges...", error);
  }
});

export const getCollegeByIdThunk = createAsyncThunk(
  "college/getCollegeById",
  async (collegeId: UUID) => {
    try {
      const apiRes = await getCollegeById(collegeId);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to fetch college...", error);
    }
  }
);

export const createCollegeThunk = createAsyncThunk(
  "college/createCollege",
  async (collegeDTO: Omit<CollegeDTO, "id">) => {
    try {
      const apiRes = await addCollege(collegeDTO);
      toastApiSuccess("College added successfully 🙂", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to create colleges...", error);
    }
  }
);

export const editCollegeThunk = createAsyncThunk(
  "college/editCollege",
  async ({ collegeId, college }: { collegeId: UUID; college: Partial<CollegeDTO> }) => {
    try {
      const apiRes = await updateCollege(collegeId, college);
      toastApiSuccess("College updated successfully", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update college", error);
    }
  }
);

export const deleteCollegeThunk = createAsyncThunk(
  "college/deleteCollege",
  async (collegeId: UUID) => {
    try {
      const apiRes = await deleteCollege(collegeId);
      toastApiSuccess("College deleted successfully", apiRes);
      return collegeId;
    } catch (error) {
      toastApiError("Failed to delete college", error);
    }
  }
);
