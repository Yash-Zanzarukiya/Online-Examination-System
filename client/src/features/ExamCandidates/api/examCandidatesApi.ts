import { ApiDataResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { CandidateState } from "../types";
import { UUID } from "crypto";

class ExamCandidatesApi {
  private readonly baseUrl = "/candidate-report";

  async fetchCandidates(scheduledExamId: UUID): Promise<ApiDataResponse<CandidateState[]>> {
    return await axiosInstance.get(`${this.baseUrl}/exam-candidate-states`, {
      params: { scheduledExamId },
    });
  }
}

const examCandidatesApi = new ExamCandidatesApi();

export default examCandidatesApi;
