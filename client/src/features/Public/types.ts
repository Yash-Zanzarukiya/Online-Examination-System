import { ApiResponse } from "@/types/ApiResponse";

// PublicState
export interface PublicState {
  status: boolean;
  isLoading: boolean;
}

// HealthCheckResponse
export type HealthCheckResponse = ApiResponse<null>;

