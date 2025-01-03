import { ApiDataResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { StudentData, StudentDataFilters, StudentProfileData, StudentProfileDTO } from "../types";
import { UUID } from "crypto";

class StudentProfileApi {
  private readonly basePath: string = "/student-profile";

  async createStudentProfile(
    profile: StudentProfileDTO
  ): Promise<ApiDataResponse<StudentProfileData>> {
    return await axiosInstance.post(this.basePath, profile);
  }

  async getStudentProfileById(
    studentProfileId: UUID
  ): Promise<ApiDataResponse<StudentProfileData>> {
    return await axiosInstance.get(`${this.basePath}/${studentProfileId}`);
  }

  async getStudentProfileByUserId(userId: UUID): Promise<ApiDataResponse<StudentProfileData>> {
    return await axiosInstance.get(`${this.basePath}/user/${userId}`);
  }

  async updateStudentProfile(
    userId: UUID,
    profile: Partial<StudentProfileDTO>
  ): Promise<ApiDataResponse<StudentProfileData>> {
    return await axiosInstance.patch(`${this.basePath}/${userId}`, profile);
  }

  async deleteStudentProfile(studentProfileId: UUID): Promise<ApiDataResponse<StudentProfileData>> {
    return await axiosInstance.delete(`${this.basePath}/${studentProfileId}`);
  }

  async uploadStudentData(
    collegeId: UUID,
    data: FormData
  ): Promise<ApiDataResponse<StudentData[]>> {
    return await axiosInstance.post(`${this.basePath}/upload`, data, {
      params: { collegeId },
      headers: { "Content-Type": "multipart/form-data" },
    });
  }

  async getAllStudentData(filters?: StudentDataFilters): Promise<ApiDataResponse<StudentData[]>> {
    return await axiosInstance.get(this.basePath, { params: filters });
  }
}

const studentProfileApi = new StudentProfileApi();

export default studentProfileApi;
