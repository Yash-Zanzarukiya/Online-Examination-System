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
    return await axiosInstance.patch(`${this.basePath}`, { ...examData, examId });
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
    examId: UUID,
    examData: ExamScheduleForm
  ): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.patch(`${this.basePath}/${examId}/update`, examData);
  }

  async updateScheduleExamStatus(
    examId: UUID,
    status: ScheduledExamStatus
  ): Promise<ApiDataResponse<ScheduledExam>> {
    return await axiosInstance.patch(`${this.basePath}/${examId}/status`, {
      params: { status },
    });
  }

  async deleteExamSchedule(examId: UUID): Promise<ApiDataResponse<string>> {
    return await axiosInstance.delete(`${this.basePath}/${examId}`);
  }
}

const scheduleExamApi = new ScheduleExamApi();

export default scheduleExamApi;
