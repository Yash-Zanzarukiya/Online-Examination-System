import questionApi from "@/features/Question/api/questionApi";
import { Question, QuestionFilter } from "@/features/Question/types/question-types";
import { ApiDataResponse } from "@/types/ApiResponse";
import { ExamQuestionResponse, examQuestionsForm, getExamQuestionsResponse } from "../types";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";

class ExamQuestionsApi {
  private readonly basePath: string = "/exam-questions";

  async addExamQuestions(
    data: examQuestionsForm
  ): Promise<ApiDataResponse<ExamQuestionResponse[]>> {
    return await axiosInstance.post(`${this.basePath}/add`, data);
  }

  async removeExamQuestion(examQuestionsId: UUID[]): Promise<ApiDataResponse<string>> {
    return await axiosInstance.post(`${this.basePath}/remove`, examQuestionsId);
  }

  async getExamQuestions(examId: UUID): Promise<ApiDataResponse<getExamQuestionsResponse[]>> {
    return await axiosInstance.get(`${this.basePath}/exam/${examId}/full`);
  }

  async getAllQuestions(filters: QuestionFilter): Promise<ApiDataResponse<Question[]>> {
    return await questionApi.getAllQuestions(filters);
  }
}

const examQuestionsApi = new ExamQuestionsApi();

export default examQuestionsApi;
