import { axiosInstance } from "@/utils";
import { StudentProfileDTO, StudentProfileResponse } from "../types";
import { UUID } from "crypto";

const createStudentProfile = async (
  profile: StudentProfileDTO
): Promise<StudentProfileResponse> => {
  return await axiosInstance.post("/student-profile", profile);
};

const updateStudentProfile = async (
  userId: UUID,
  profile: Partial<StudentProfileDTO>
): Promise<StudentProfileResponse> => {
  return await axiosInstance.patch(`/student-profile/${userId}`, profile);
};

const getStudentProfileByUserId = async (userId: UUID): Promise<StudentProfileResponse> => {
  return await axiosInstance.get(`/student-profile/user/${userId}`);
};

const deleteStudentProfile = async (userId: UUID): Promise<StudentProfileResponse> => {
  return await axiosInstance.delete(`/student-profile/${userId}`);
};

const studentProfileApis = {
  createStudentProfile,
  updateStudentProfile,
  getStudentProfileByUserId,
  deleteStudentProfile,
};

export default studentProfileApis;
