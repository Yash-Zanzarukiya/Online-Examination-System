import { ApiDataResponse } from "@/types/ApiResponse";
import { Exam } from "../types";
import { ExamForm } from "../validators";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";

class ExamApi {
  private readonly examBasePath: string = "/exams";

  async createExam(examData: ExamForm): Promise<ApiDataResponse<Exam>> {
    return await axiosInstance.post(this.examBasePath, examData);
  }

  async getExamById(examId: UUID): Promise<ApiDataResponse<Exam>> {
    return await axiosInstance.get(`${this.examBasePath}/${examId}`);
  }

  async getAllExams(drafted: boolean = false): Promise<ApiDataResponse<Exam[]>> {
    return await axiosInstance.get(this.examBasePath, { params: { drafted } });
  }

  async updateExam(examId: UUID, examData: ExamForm): Promise<ApiDataResponse<Exam>> {
    return await axiosInstance.patch(`${this.examBasePath}/${examId}`, examData);
  }

  async deleteExam(examId: UUID): Promise<ApiDataResponse<string>> {
    return await axiosInstance.delete(`${this.examBasePath}/${examId}`);
  }
}

const examApi = new ExamApi();

export default examApi;
