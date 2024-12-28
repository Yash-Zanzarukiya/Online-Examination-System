import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getAllExams } from "../redux/examThunks";

export default function useAllExams(drafted?: boolean) {
  const dispatch = useAppDispatch();

  const { exams, isLoading } = useAppSelector(({ exams }) => exams);

  useEffect(() => {
    dispatch(getAllExams(drafted));
  }, [dispatch]);

  return { exams, isLoading };
}
