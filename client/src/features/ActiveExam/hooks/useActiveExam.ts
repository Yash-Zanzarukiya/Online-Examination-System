import { useEffect } from "react";
import {
  navigateQuestion,
  setAnswer,
  decrementTime,
  submitExam,
  startExam,
} from "../redux/activeExamSlice";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { ActiveExamQuestion } from "../types";

export const useActiveExam = () => {
  const dispatch = useAppDispatch();
  const examState = useAppSelector(({ activeExam }) => activeExam);

  useEffect(() => {
    if (examState.isExamStarted && !examState.isExamSubmitted) {
      const timer = setInterval(() => {
        dispatch(decrementTime());
      }, 1000);

      return () => clearInterval(timer);
    }
  }, [examState.isExamStarted, examState.isExamSubmitted, dispatch]);

  useEffect(() => {
    if (examState.timeRemaining === 0) {
      dispatch(submitExam());
    }
  }, [examState.timeRemaining, dispatch]);

  const handleAnswerChange = (questionId: string, answerId: string) => {
    dispatch(setAnswer({ questionId, answerId }));
  };

  const handleQuestionNavigation = (index: number) => {
    dispatch(navigateQuestion(index));
  };

  const handleExamSubmit = () => {
    dispatch(submitExam());
  };

  const handleStartExam = () => {
    dispatch(startExam());
  };

  const handleSaveAndNext = () => {
    const currentQuestion = examState.examQuestions[examState.currentQuestionIndex];
    if (
      examState.answers[currentQuestion.question.id] &&
      examState.currentQuestionIndex < examState.examQuestions.length - 1
    ) {
      handleQuestionNavigation(examState.currentQuestionIndex + 1);
    }
  };

  const currentQuestion = (
    examState.examQuestions.length ? examState.examQuestions[examState.currentQuestionIndex] : null
  ) as ActiveExamQuestion;

  return {
    examState,
    currentQuestion,
    questions: examState.examQuestions,
    handleAnswerChange,
    handleQuestionNavigation,
    handleExamSubmit,
    handleStartExam,
    handleSaveAndNext,
  };
};

export default useActiveExam;
