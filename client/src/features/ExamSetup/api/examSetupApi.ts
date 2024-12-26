import { ApiResponse } from "@/types/ApiResponse";
import { axiosInstance, navigateTo, toastApiError, toastApiSuccess } from "@/utils";
import { ActiveExamQuestion } from "../../ActiveExam/types";
import { UUID } from "crypto";
import { ExamLoginDTO } from "@/features/ExamSetup/types";

class ExamSetupApi {
  async loginToExam(examLoginDTO: ExamLoginDTO) {
    try {
      const apiRes: ApiResponse<string> = await axiosInstance.post(
        `/exam-attempt/make-attempt`,
        examLoginDTO
      );
      toastApiSuccess("Logged in successfully", apiRes, 100);
      navigateTo(`/exam/set-up/${examLoginDTO.scheduledExamId}`);
    } catch (error) {
      toastApiError("Failed to make attempt", error);
    }
  }

  async getQuestionsForExam(examId: UUID): Promise<ApiResponse<{ data: ActiveExamQuestion[] }>> {
    return await axiosInstance.get(`/active-exam/questions/${examId}`);
  }
}

const examSetupApi = new ExamSetupApi();

export default examSetupApi;
