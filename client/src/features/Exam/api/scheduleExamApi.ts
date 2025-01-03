import { ApiDataResponse } from "@/types/ApiResponse";
import { ScheduledExam, ScheduledExamFilter } from "../types";
import { ExamScheduleForm } from "../validators";
import { axiosInstance } from "@/utils";
import { ScheduledExamStatus } from "@/features/ActiveExam/types";
import { UUID } from "crypto";

class ScheduleExamApi {
  private readonly basePath: string = "/schedule-exam";

  async scheduleExam(
    examId: UUID,
    examData: ExamScheduleForm
  ): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.post(`${this.basePath}`, { ...examData, examId });
  }

  async getExamScheduleById(examId: UUID): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.get(`${this.basePath}/${examId}`);
  }

  async getScheduledExams(
    filters: ScheduledExamFilter = {}
  ): Promise<ApiDataResponse<ScheduledExam[]>> {
    return await axiosInstance.get(this.basePath, { params: filters });
  }

  async updateExamSchedule(
    scheduledExamId: UUID,
    examData: ExamScheduleForm
  ): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.patch(`${this.basePath}/${scheduledExamId}/update`, examData);
  }

  async updateScheduleExamStatus(
    scheduledExamId: UUID,
    status: ScheduledExamStatus
  ): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.patch(`${this.basePath}/${scheduledExamId}/status`, null, {
      params: { status },
    });
  }

  async deleteExamSchedule(scheduledExamId: UUID): Promise<ApiDataResponse<string>> {
    return await axiosInstance.delete(`${this.basePath}/${scheduledExamId}`);
  }

  async inviteCandidates(
    scheduledExamId: UUID,
    formData: FormData
  ): Promise<ApiDataResponse<string[]>> {
    return await axiosInstance.post(`${this.basePath}/${scheduledExamId}/invite`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  }
}

const scheduleExamApi = new ScheduleExamApi();

export default scheduleExamApi;
