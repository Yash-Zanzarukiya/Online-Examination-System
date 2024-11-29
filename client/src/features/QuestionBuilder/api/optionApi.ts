import { McqOptionForm, McqOption } from "../types";
import { ApiResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";

const createMcqOptionApi = async (
  payload: McqOptionForm
): Promise<ApiResponse<{ data: McqOption }>> => {
  return await axiosInstance.post("/mcq-options", payload);
};

const createMultipleMcqOptionsApi = async (
  payload: McqOptionForm[]
): Promise<ApiResponse<{ data: McqOption[] }>> => {
  return await axiosInstance.post("/mcq-options/multiple", payload);
};

const getOptionsByQuestionIdApi = async (
  questionId: UUID
): Promise<ApiResponse<{ data: McqOption[] }>> => {
  return await axiosInstance.get(`/mcq-options/question/${questionId}`);
};

const getMcqOptionByIdApi = async (optionId: UUID): Promise<ApiResponse<{ data: McqOption }>> => {
  return await axiosInstance.get(`/mcq-options/${optionId}`);
};

const updateMcqOptionApi = async (
  optionId: UUID,
  optionData: McqOptionForm
): Promise<ApiResponse<{ data: McqOption }>> => {
  return await axiosInstance.patch(`/mcq-options/${optionId}`, optionData);
};

const deleteMcqOptionApi = async (optionId: UUID): Promise<ApiResponse<{ data: string }>> => {
  return await axiosInstance.delete(`/mcq-options/${optionId}`);
};

const deleteOptionsByQuestionIdApi = async (
  questionId: UUID
): Promise<ApiResponse<{ data: string }>> => {
  return await axiosInstance.delete(`/mcq-options/question/${questionId}`);
};

const deleteMcqOptionImageApi = async (optionId: UUID): Promise<ApiResponse<{ data: string }>> => {
  return await axiosInstance.delete(`/mcq-options/${optionId}/image`);
};

const mcqOptionApi = {
  createMcqOptionApi,
  createMultipleMcqOptionsApi,
  getOptionsByQuestionIdApi,
  getMcqOptionByIdApi,
  updateMcqOptionApi,
  deleteMcqOptionApi,
  deleteOptionsByQuestionIdApi,
  deleteMcqOptionImageApi,
};

export default mcqOptionApi;
