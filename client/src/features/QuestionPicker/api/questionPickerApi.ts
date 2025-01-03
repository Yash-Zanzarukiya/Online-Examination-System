import { Question, QuestionFilter } from "@/features/QuestionBuilder/types";
import { ApiResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import questionApi from "@/features/QuestionBuilder/api/questionApi";
import { ExamQuestionResponse, examQuestionsForm, getExamQuestionsResponse } from "../types";
import { UUID } from "crypto";

class QuestionPickerApi {
  async getAllQuestions(filters: QuestionFilter): Promise<ApiResponse<{ data: Question[] }>> {
    return await questionApi.getAllQuestionsApi(filters);
  }

  async getExamQuestions(
    examId: string
  ): Promise<ApiResponse<{ data: getExamQuestionsResponse[] }>> {
    return await axiosInstance.get(`/exam-questions/exam/${examId}/full`);
  }

  async addExamQuestions(
    payload: examQuestionsForm
  ): Promise<ApiResponse<{ data: ExamQuestionResponse[] }>> {
    return await axiosInstance.post(`/exam-questions`, payload);
  }

  async removeExamQuestion(payload: UUID[]): Promise<ApiResponse<string>> {
    return await axiosInstance.post(`/exam-questions`, payload);
  }
}

const questionPickerApi = new QuestionPickerApi();

export default questionPickerApi;
