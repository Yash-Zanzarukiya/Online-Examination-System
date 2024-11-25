import { createAsyncThunk } from "@reduxjs/toolkit";
import { StudentProfileDTO } from "../types";
import studentProfileApi from "../api/studentApi";
import { toastApiError, toastApiSuccess } from "@/utils";
import { UUID } from "crypto";

export const createStudentProfileThunk = createAsyncThunk(
  "studentProfile/create",
  async (profile: StudentProfileDTO) => {
    try {
      const apiRes = await studentProfileApi.createStudentProfile(profile);
      toastApiSuccess("Profile Created Successfully", apiRes);
      return apiRes.data.data;
    } catch (error: any) {
      toastApiError("Failed to create profile", error);
    }
  }
);

export const updateStudentProfileThunk = createAsyncThunk(
  "studentProfile/update",
  async ({ profileId, profile }: { profileId: UUID; profile: Partial<StudentProfileDTO> }) => {
    try {
      const apiRes = await studentProfileApi.updateStudentProfile(profileId, profile);
      toastApiSuccess("Profile Updated Successfully", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to update profile", error);
    }
  }
);

export const getStudentProfileThunk = createAsyncThunk(
  "studentProfile/get",
  async (userId: UUID) => {
    try {
      const apiRes = await studentProfileApi.getStudentProfileByUserId(userId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to fetch profile", error);
    }
  }
);

export const deleteStudentProfileThunk = createAsyncThunk(
  "studentProfile/delete",
  async (userId: UUID) => {
    try {
      const apiRes = await studentProfileApi.deleteStudentProfile(userId);
      toastApiSuccess("Profile Deleted Successfully", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to delete profile", error);
    }
  }
);
