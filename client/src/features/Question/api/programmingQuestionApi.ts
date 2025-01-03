import { ApiDataResponse } from "@/types/ApiResponse";
import { ProgrammingQuestion } from "../types/programming-types";
import { ProgrammingQuestionForm } from "../validators/programming-validator";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";
import { Difficulty } from "@/types/Difficulty";

class ProgrammingQuestionApi {
  private readonly basePath: string = "/programming-questions";

  async createProgrammingQuestion(
    data: ProgrammingQuestionForm
  ): Promise<ApiDataResponse<ProgrammingQuestion>> {
    return await axiosInstance.post(this.basePath, data);
  }

  async createBulkProgrammingQuestions(
    data: ProgrammingQuestionForm[]
  ): Promise<ApiDataResponse<ProgrammingQuestion[]>> {
    return await axiosInstance.post(`${this.basePath}/bulk`, data);
  }

  async getProgrammingQuestionById(id: UUID): Promise<ApiDataResponse<ProgrammingQuestion>> {
    return await axiosInstance.get(`${this.basePath}/${id}`);
  }

  async getProgrammingQuestionByQuestionId(
    questionId: UUID
  ): Promise<ApiDataResponse<ProgrammingQuestion>> {
    return await axiosInstance.get(`${this.basePath}/question/${questionId}`);
  }

  async getAllProgrammingQuestions(
    difficulty?: Difficulty
  ): Promise<ApiDataResponse<ProgrammingQuestion[]>> {
    return await axiosInstance.get(this.basePath, { params: { difficulty } });
  }

  async updateProgrammingQuestion(
    questionId: UUID,
    data: ProgrammingQuestionForm
  ): Promise<ApiDataResponse<ProgrammingQuestion>> {
    return await axiosInstance.patch(`${this.basePath}/${questionId}`, data);
  }

  async deleteProgrammingQuestion(questionId: UUID): Promise<ApiDataResponse<ProgrammingQuestion>> {
    return await axiosInstance.delete(`${this.basePath}/${questionId}`);
  }
}

const programmingQuestionApi = new ProgrammingQuestionApi();

export default programmingQuestionApi;
