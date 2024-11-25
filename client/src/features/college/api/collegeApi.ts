import { axiosInstance } from "@/utils";
import {
  AddCollegeResponse,
  UpdateCollegeResponse,
  GetCollegesResponse,
  DeleteCollegeResponse,
  GetCollegeByIdResponse,
  CollegeDTO,
} from "../types";

export const addCollege = async (college: Omit<CollegeDTO, "id">): Promise<AddCollegeResponse> => {
  return await axiosInstance.post("/colleges", college);
};

export const updateCollege = async (
  collegeId: string,
  college: Partial<CollegeDTO>
): Promise<UpdateCollegeResponse> => {
  return await axiosInstance.patch(`/colleges/${collegeId}`, college);
};

export const getColleges = async (): Promise<GetCollegesResponse> => {
  return await axiosInstance.get("/colleges");
};

export const deleteCollege = async (collegeId: string): Promise<DeleteCollegeResponse> => {
  return await axiosInstance.delete(`/colleges/${collegeId}`);
};

export const getCollegeById = async (collegeId: string): Promise<GetCollegeByIdResponse> => {
  return await axiosInstance.get(`/colleges/${collegeId}`);
};
