import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getScheduledExams } from "../redux/examThunks";

export default function useScheduledExams() {
  const dispatch = useAppDispatch();

  const { scheduledExams, isLoading } = useAppSelector(({ exams }) => exams);

  useEffect(() => {
    dispatch(getScheduledExams());
  }, [dispatch]);

  return { scheduledExams, isLoading };
}
