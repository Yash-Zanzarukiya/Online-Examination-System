import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getAllExams } from "../redux/examThunks";

export default function useExams() {
  const dispatch = useAppDispatch();

  const { exams, isLoading } = useAppSelector(({ exams }) => exams);

  useEffect(() => {
    dispatch(getAllExams());
  }, [dispatch]);

  return { exams, isLoading };
}
