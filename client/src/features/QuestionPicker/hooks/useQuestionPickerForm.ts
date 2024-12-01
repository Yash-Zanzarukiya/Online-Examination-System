import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { Question, QuestionFilter } from "@/features/QuestionBuilder/types";
import { useEffect, useMemo, useState } from "react";
import {
  addExamQuestions,
  fetchAllQuestions,
  fetchExamQuestions,
  removeExamQuestion,
} from "../redux/questionPickerThunks";
import { setExamId } from "../redux/questionPickerSlice";
import { UUID } from "crypto";
import { useParams } from "react-router-dom";

function useQuestionPickerForm() {
  const dispatch = useAppDispatch();

  const { examId: examIdParam } = useParams();

  const { examId, allQuestions, examQuestions, initialExamQuestions, isLoading } = useAppSelector(
    ({ questionPicker }) => questionPicker
  );

  const [availableQuestions, setAvailableQuestions] = useState<Question[]>([]);
  const [selectedQuestions, setSelectedQuestions] = useState<Question[]>([]);
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const examQuestionIds = useMemo(() => examQuestions.map((eq) => eq.questionId), [examQuestions]);

  useEffect(() => {
    setSelectedQuestions(initialExamQuestions);
  }, [initialExamQuestions]);

  useEffect(() => {
    const allQue = allQuestions.filter((que) => !examQuestionIds.includes(que.id));
    setAvailableQuestions(allQue);
  }, [allQuestions, examQuestions]);

  useEffect(() => {
    if (!examIdParam) return;
    dispatch(fetchExamQuestions(examIdParam as UUID));
    dispatch(setExamId(examIdParam as UUID));
  }, [examIdParam]);

  const loadQuestions = async (filters: QuestionFilter) => {
    dispatch(fetchAllQuestions(filters));
  };

  const handleAddQuestion = (sq: Question) => {
    setSelectedQuestions((prev) => [sq, ...prev]);
    setAvailableQuestions((prev) => prev.filter((que) => que.id !== sq.id));
  };

  const handleRemoveQuestion = (rq: Question) => {
    setSelectedQuestions((prev) => prev.filter((que) => que.id !== rq.id));
    setAvailableQuestions((prev) => [...prev, rq]);
  };

  const clearAllQuestions = () => {
    setAvailableQuestions((prev) => [...selectedQuestions, ...prev]);
    setSelectedQuestions(() => []);
  };

  const handleSubmit = async () => {
    setIsSubmitting(true);
    const selectedQuestionIds = selectedQuestions.map((que) => que.id);
    const newQuestionIds = selectedQuestionIds.filter((id) => !examQuestionIds.includes(id));
    if (newQuestionIds.length)
      await dispatch(addExamQuestions({ examId: examId as UUID, questionIds: newQuestionIds }));
    const removedExamQuestionIds = examQuestions
      .filter((eq) => !selectedQuestionIds.includes(eq.questionId))
      .map((eq) => eq.id);
    if (removedExamQuestionIds.length) await dispatch(removeExamQuestion(removedExamQuestionIds));
    setIsSubmitting(false);
  };

  return {
    availableQuestions,
    selectedQuestions,
    isLoading,
    isSubmitting,
    loadQuestions,
    handleAddQuestion,
    handleRemoveQuestion,
    clearAllQuestions,
    handleSubmit,
  };
}

export default useQuestionPickerForm;
