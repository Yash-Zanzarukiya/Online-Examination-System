import { ApiResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { ActiveExamQuestion } from "../types";
import { UUID } from "crypto";

class ActiveExamApi {
  async getQuestionsForExam(examId: UUID): Promise<ApiResponse<{ data: ActiveExamQuestion[] }>> {
    return await axiosInstance.get(`active-exam/${examId}/questions`);
  }
}

const activeExamApi = new ActiveExamApi();

export default activeExamApi;
