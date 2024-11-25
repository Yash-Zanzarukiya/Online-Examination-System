import { axiosInstance } from "@/utils";
import { HealthCheckResponse } from "../types";

export const checkHealth = async (): Promise<HealthCheckResponse> => {
  const response = await axiosInstance.get("/public/health");
  return response.data;
};
