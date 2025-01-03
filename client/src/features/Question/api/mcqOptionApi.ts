import { ApiDataResponse } from "@/types/ApiResponse";
import { McqOptionForm } from "../validators/mcq-validator";
import { McqOption } from "../types/mcq-types";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";

class McqOptionApi {
  private readonly basePath: string = "/mcq-options";

  async createMcqOption(data: McqOptionForm): Promise<ApiDataResponse<McqOption>> {
    return await axiosInstance.post(this.basePath, data);
  }

  async createBulkMcqOptions(data: McqOptionForm[]): Promise<ApiDataResponse<McqOption[]>> {
    return await axiosInstance.post(`${this.basePath}/bulk`, data);
  }

  async getMcqOptionById(mcqOptionId:UUID): Promise<ApiDataResponse<McqOption>> {
    return await axiosInstance.get(`${this.basePath}/${mcqOptionId}`);
  }

  async getAllMcqOptionsByQuestionId(questionId: UUID): Promise<ApiDataResponse<McqOption[]>> {
    return await axiosInstance.get(`${this.basePath}/question/${questionId}`);
  }

  async updateMcqOption(mcqOptionId: UUID, data: McqOptionForm): Promise<ApiDataResponse<McqOption>> {
    return await axiosInstance.patch(`${this.basePath}/${mcqOptionId}`, data);
  }

  async updateBulkMcqOptions(data: McqOptionForm[]): Promise<ApiDataResponse<McqOption[]>> {
    return await axiosInstance.patch(`${this.basePath}/bulk`, data);
  }

  async deleteMcqOption(mcqOptionId: UUID): Promise<ApiDataResponse<McqOption>> {
    return await axiosInstance.delete(`${this.basePath}/${mcqOptionId}`);
  }

  async deleteMcqOptionImage(mcqOptionId: UUID): Promise<ApiDataResponse<null>> {
    return await axiosInstance.delete(`${this.basePath}/${mcqOptionId}/image`);
  }
}

const mcqOptionApi = new McqOptionApi();

export default mcqOptionApi;
