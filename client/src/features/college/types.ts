import { UUID } from "crypto";
import { ApiResponse } from "@/types/ApiResponse";

export interface CollegeState {
  isLoading: boolean;
  colleges: CollegeData | [];
}

export type CollegeData = CollegeDTO[];

export interface CollegeDTO {
  id: UUID;
  name: string;
}

export type AddCollegeResponse = ApiResponse<{ data: CollegeDTO }>;

export type UpdateCollegeResponse = ApiResponse<{ data: CollegeDTO }>;

export type GetCollegesResponse = ApiResponse<{ data: CollegeDTO[] }>;

export type DeleteCollegeResponse = ApiResponse<null>;

export type GetCollegeByIdResponse = ApiResponse<{ data: CollegeDTO }>;
