import { createAsyncThunk } from "@reduxjs/toolkit";
import { toastApiError, toastApiSuccess } from "@/utils";
import { UUID } from "crypto";
import { ExamForm, ExamScheduleForm } from "../validators";
import examApi from "../api/examApis";
import scheduleExamApi from "../api/scheduleExamApi";
import { ScheduledExamFilter } from "../types";
import { ScheduledExamStatus } from "@/features/ActiveExam/types";

export const createExam = createAsyncThunk("exam/create", async (examData: ExamForm) => {
  try {
    const apiRes = await examApi.createExam(examData);
    toastApiSuccess("Success 🙂", apiRes);
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Create Exam", error);
  }
});

export const getAllExams = createAsyncThunk("exam/getAll", async (drafted?: boolean) => {
  try {
    const apiRes = await examApi.getAllExams(drafted);
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Fetch Exams", error);
  }
});

export const getExamById = createAsyncThunk("exam/getById", async (examId: UUID) => {
  try {
    const apiRes = await examApi.getExamById(examId);
    return apiRes.data.data;
  } catch (error) {
    toastApiError("Failed to Fetch Exam", error);
  }
});

export const updateExam = createAsyncThunk(
  "exam/update",
  async ({ examId, examData }: { examId: UUID; examData: ExamForm }) => {
    try {
      const apiRes = await examApi.updateExam(examId, examData);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Update Exam", error);
    }
  }
);

export const deleteExam = createAsyncThunk("exam/delete", async (examId: UUID) => {
  try {
    const apiRes = await examApi.deleteExam(examId);
    toastApiSuccess("Success 🙂", apiRes);
    return examId;
  } catch (error) {
    toastApiError("Failed to Delete Exam", error);
  }
});

export const scheduleExam = createAsyncThunk(
  "exam/schedule/create",
  async ({ examId, examData }: { examId: UUID; examData: ExamScheduleForm }) => {
    try {
      const apiRes = await scheduleExamApi.scheduleExam(examId, examData);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Schedule Exam", error);
    }
  }
);

export const getExamScheduleById = createAsyncThunk(
  "exam/schedule/getById",
  async (scheduledExamId: UUID) => {
    try {
      const apiRes = await scheduleExamApi.getExamScheduleById(scheduledExamId);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Fetch Exam Schedule", error);
    }
  }
);

export const getScheduledExams = createAsyncThunk(
  "exam/schedule/getAll",
  async (filters?: ScheduledExamFilter) => {
    try {
      const apiRes = await scheduleExamApi.getScheduledExams(filters);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Fetch Scheduled Exams", error);
    }
  }
);

export const updateExamSchedule = createAsyncThunk(
  "exam/schedule/update",
  async ({ scheduledExamId, examData }: { scheduledExamId: UUID; examData: ExamScheduleForm }) => {
    try {
      const apiRes = await scheduleExamApi.updateExamSchedule(scheduledExamId, examData);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Update Exam Schedule", error);
    }
  }
);

export const updateScheduleExamStatus = createAsyncThunk(
  "exam/schedule/updateStatus",
  async ({ scheduledExamId, status }: { scheduledExamId: UUID; status: ScheduledExamStatus }) => {
    try {
      const apiRes = await scheduleExamApi.updateScheduleExamStatus(scheduledExamId, status);
      toastApiSuccess("Success 🙂", apiRes);
      return status;
    } catch (error) {
      toastApiError("Failed to Update Exam Schedule Status", error);
    }
  }
);

export const deleteExamSchedule = createAsyncThunk(
  "exam/schedule/delete",
  async (scheduledExamId: UUID) => {
    try {
      const apiRes = await scheduleExamApi.deleteExamSchedule(scheduledExamId);
      toastApiSuccess("Success 🙂", apiRes);
      return scheduledExamId;
    } catch (error) {
      toastApiError("Failed to Delete Exam Schedule", error);
    }
  }
);

export const inviteCandidates = createAsyncThunk(
  "exam/schedule/invite",
  async ({ scheduledExamId, formData }: { scheduledExamId: UUID; formData: FormData }) => {
    try {
      const apiRes = await scheduleExamApi.inviteCandidates(scheduledExamId, formData);
      toastApiSuccess("Success 🙂", apiRes);
      return apiRes.data.data;
    } catch (error) {
      toastApiError("Failed to Invite Candidates", error);
    }
  }
);
