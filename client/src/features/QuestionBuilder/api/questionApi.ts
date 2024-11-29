import {
  QuestionCreatePayload,
  QuestionForm,
  QuestionFilter,
  Question,
  FullQuestion,
} from "../types";
import { ApiResponse } from "@/types/ApiResponse";
import { axiosInstance } from "@/utils";
import { UUID } from "crypto";

const createQuestionWithOptionsApi = async (
  payload: QuestionCreatePayload
): Promise<ApiResponse<{ data: FullQuestion }>> => {
  return await axiosInstance.post("/questions/with-options", payload);
};

const createMultipleQuestionsWithOptionsApi = async (
  payload: QuestionCreatePayload[]
): Promise<ApiResponse<{ data: FullQuestion[] }>> => {
  return await axiosInstance.post("/questions/with-options/multiple", payload);
};

const createQuestionApi = async (
  payload: QuestionForm
): Promise<ApiResponse<{ data: Question }>> => {
  return await axiosInstance.post("/questions", payload);
};

const createMultipleQuestionsApi = async (
  payload: QuestionForm[]
): Promise<ApiResponse<{ data: Question[] }>> => {
  return await axiosInstance.post("/questions/multiple", payload);
};

const getQuestionByIdApi = async (questionId: UUID): Promise<ApiResponse<{ data: Question }>> => {
  return await axiosInstance.get(`/questions/${questionId}`);
};

const getAllQuestionsApi = async (
  filter: QuestionFilter
): Promise<ApiResponse<{ data: Question[] }>> => {
  const { categoryId, difficulty, type } = filter;
  return await axiosInstance.get("/questions", {
    params: { categoryId, difficulty, type },
  });
};

const getFullQuestionByIdApi = async (
  questionId: UUID
): Promise<ApiResponse<{ data: FullQuestion }>> => {
  return await axiosInstance.get(`/questions/full/${questionId}`);
};

const getAllFullQuestionsApi = async (
  filter: QuestionFilter
): Promise<ApiResponse<{ data: FullQuestion[] }>> => {
  const { categoryId, difficulty, type } = filter;
  return await axiosInstance.get("/questions/all/full", {
    params: { categoryId, difficulty, type },
  });
};

const updateQuestionApi = async (
  questionId: UUID,
  questionData: QuestionForm
): Promise<ApiResponse<{ data: Question }>> => {
  return await axiosInstance.patch(`/questions/${questionId}`, questionData);
};

const deleteQuestionApi = async (questionId: UUID): Promise<ApiResponse<{ data: string }>> => {
  return await axiosInstance.delete(`/questions/${questionId}`);
};

const deleteQuestionImageApi = async (questionId: UUID): Promise<ApiResponse<{ data: string }>> => {
  return await axiosInstance.delete(`/questions/${questionId}/image`);
};

const questionApi = {
  createQuestionWithOptionsApi,
  createMultipleQuestionsWithOptionsApi,
  createQuestionApi,
  createMultipleQuestionsApi,
  getQuestionByIdApi,
  getAllQuestionsApi,
  getFullQuestionByIdApi,
  getAllFullQuestionsApi,
  updateQuestionApi,
  deleteQuestionApi,
  deleteQuestionImageApi,
};

export default questionApi;
