import { ApiResponse } from "@/types/ApiResponse";
import toastMessage from "./toastMessage";

export default function toastSuccess(title?: string, response?: ApiResponse, duration = 2000) {
  const message = response?.data?.message;

  toastMessage(title || "Success...", message || "", true, duration);
}
