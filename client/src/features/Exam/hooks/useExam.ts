import { UUID } from "crypto";
import { useEffect, useState } from "react";
import { useAppSelector, useAppDispatch } from "@/app/hooks";
import { resetExam, setExam } from "../redux/examSlice";
import { getExamById } from "../redux/examThunks";

function useExam(examId: UUID) {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const { exams, exam } = useAppSelector(({ exams }) => exams);

  useEffect(() => {
    setLoading(true);
    if (!examId) return;

    const exam = exams.find((exam) => exam.id === examId);

    if (exam) {
      dispatch(setExam(exam));
    } else {
      dispatch(getExamById(examId));
    }

    setLoading(false);
    return () => {
      dispatch(resetExam());
    };
  }, [examId]);

  return { exam, loading };
}

export default useExam;
