import { useEffect, useState } from "react";
import scheduleExamApi from "../api/scheduleExamApi";
import { ScheduledExam } from "../types";
import { toastApiError } from "@/utils";
import { UUID } from "crypto";

function useExamSchedules(examId: UUID) {
  const [examSchedules, setExamSchedules] = useState<ScheduledExam[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchExamSchedules = async () => {
      setIsLoading(true);
      try {
        const apiRes = await scheduleExamApi.getScheduledExams({ examId: examId });
        setExamSchedules(apiRes.data.data);
      } catch (error) {
        toastApiError("Failed to fetch schedules", error);
      } finally {
        setIsLoading(false);
      }
    };
    if (examId) fetchExamSchedules();
  }, [examId]);

  return { examSchedules, isLoading };
}

export default useExamSchedules;
