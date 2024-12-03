import { getQuestionsForExam } from "../redux/activeExamThunks";
import { useParams } from "react-router-dom";
import { UUID } from "crypto";
import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "@/app/hooks";

const useActiveExamQuestions = () => {
  const dispatch = useAppDispatch();
  const { examId } = useParams();
  const { examQuestions, isFetchingQuestions } = useAppSelector(({ activeExam }) => activeExam);

  useEffect(() => {
    if (examId) dispatch(getQuestionsForExam(examId as UUID));
  }, [examId]);

  return { examQuestions, isFetchingQuestions };
};

export default useActiveExamQuestions;
