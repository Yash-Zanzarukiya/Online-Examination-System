import { ApiDataResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { QuestionForm } from "../validators/question-validator";
import { Question, QuestionFilter } from "../types/question-types";
import { UUID } from "crypto";

class QuestionApi {
  private readonly basePath: string = "/questions";

  async createQuestion(data: QuestionForm): Promise<ApiDataResponse<Question>> {
    return await axiosInstance.post(this.basePath, data);
  }

  async createBulkQuestions(data: QuestionForm[]): Promise<ApiDataResponse<Question[]>> {
    return await axiosInstance.post(`${this.basePath}/bulk`, data);
  }

  async uploadQuestions(data: FormData): Promise<ApiDataResponse<Question[]>> {
    return await axiosInstance.post(`${this.basePath}/upload`, data, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  }

  async getQuestionById(questionId: UUID): Promise<ApiDataResponse<Question>> {
    return await axiosInstance.get(`${this.basePath}/${questionId}`);
  }

  async getAllQuestions(filter?: QuestionFilter): Promise<ApiDataResponse<Question[]>> {
    return await axiosInstance.get(this.basePath, { params: filter });
  }

  async updateQuestion(questionId: UUID, data: QuestionForm): Promise<ApiDataResponse<Question>> {
    return await axiosInstance.patch(`${this.basePath}/${questionId}`, data);
  }

  async deleteQuestion(questionId: UUID): Promise<ApiDataResponse<Question>> {
    return await axiosInstance.delete(`${this.basePath}/${questionId}`);
  }

  async deleteQuestionImage(questionId: UUID): Promise<ApiDataResponse<null>> {
    return await axiosInstance.delete(`${this.basePath}/${questionId}/image`);
  }
}

const questionApi = new QuestionApi();

export default questionApi;
