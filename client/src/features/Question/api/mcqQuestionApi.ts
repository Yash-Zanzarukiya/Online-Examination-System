import { ApiDataResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { McqQuestion } from "../types/mcq-types";
import { McqQuestionForm } from "../validators/mcq-validator";
import { QuestionFilter } from "../types/question-types";
import { UUID } from "crypto";

class McqQuestionApi {
  private readonly basePath: string = "/mcq-questions";

  async createMcqQuestion(data: McqQuestionForm): Promise<ApiDataResponse<McqQuestion>> {
    return await axiosInstance.post(this.basePath, data);
  }

  async createBulkMcqQuestions(data: McqQuestionForm[]): Promise<ApiDataResponse<McqQuestion[]>> {
    return await axiosInstance.post(`${this.basePath}/bulk`, data);
  }

  async getMcqQuestionById(questionId: UUID): Promise<ApiDataResponse<McqQuestion>> {
    return await axiosInstance.get(`${this.basePath}/${questionId}`);
  }

  async getAllMcqQuestions(filters: QuestionFilter = {}): Promise<ApiDataResponse<McqQuestion[]>> {
    return await axiosInstance.get(this.basePath, { params: filters });
  }
}

const mcqQuestionApi = new McqQuestionApi();

export default mcqQuestionApi;
