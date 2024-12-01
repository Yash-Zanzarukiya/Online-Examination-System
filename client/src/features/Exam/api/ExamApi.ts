import { axiosInstance } from "@/utils";
import { Exam, ExamForm } from "../types";
import { ApiResponse } from "@/types/ApiResponse";
import { UUID } from "crypto";

class ExamApi {
  async createExam(examData: ExamForm): Promise<ApiResponse<{ data: Exam }>> {
    return await axiosInstance.post("/exams", examData);
  }

  async getAllExams(): Promise<ApiResponse<{ data: Exam[] }>> {
    return await axiosInstance.get("/exams");
  }

  async getExamById(examId: UUID): Promise<ApiResponse<{ data: Exam }>> {
    return await axiosInstance.get(`/exams/${examId}`);
  }

  async updateExam(examId: UUID, examData: ExamForm): Promise<ApiResponse<{ data: Exam }>> {
    return await axiosInstance.patch(`/exams/${examId}`, examData);
  }

  async deleteExam(examId: UUID): Promise<ApiResponse<string>> {
    return await axiosInstance.delete(`/exams/${examId}`);
  }
}

const examApi = new ExamApi();

export default examApi;
