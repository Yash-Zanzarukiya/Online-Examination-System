import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect, useState } from "react";
import { resetScheduledExam, setScheduledExam } from "../redux/examSlice";
import { getExamScheduleById } from "../redux/examThunks";
import { UUID } from "crypto";

function useScheduledExam(scheduledExamId: UUID) {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const { scheduledExams, scheduledExam } = useAppSelector(({ exams }) => exams);

  useEffect(() => {
    setLoading(true);
    if (!scheduledExamId) return;

    const se = scheduledExams.find((se) => se.id === scheduledExamId);

    if (se) {
      dispatch(setScheduledExam(se));
    } else {
      dispatch(getExamScheduleById(scheduledExamId));
    }

    setLoading(false);
    return () => {
      dispatch(resetScheduledExam());
    };
  }, [scheduledExamId]);

  return { scheduledExam, loading };
}

export default useScheduledExam;
