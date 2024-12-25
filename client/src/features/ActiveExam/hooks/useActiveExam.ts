import { useEffect, useCallback } from "react";
import {
  navigateQuestion,
  setAnswer,
  setCode,
  decrementTime,
  submitExam,
  startExam,
} from "../redux/activeExamSlice";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { ActiveExamQuestion } from "../types";
import { UUID } from "crypto";
import { useWebSocket } from "@/hooks";
import {
  ActionType,
  MessageType,
  SubmissionType,
} from "@/features/ExamWebSocket/types/message-types";

export const useActiveExam = () => {
  const client = useWebSocket();
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

  const handleAnswerChange = useCallback(
    (questionId: UUID, selectedOptionId: UUID) => {
      dispatch(setAnswer({ questionId, selectedOptionId }));
      client.send({
        type: MessageType.SUBMISSION,
        subtype: SubmissionType.MULTIPLE_CHOICE_SUBMISSION,
        payload: { questionId, selectedOptionId },
      });
    },
    [dispatch]
  );

  const handleCodeChange = useCallback(
    (questionId: UUID, submittedCode: string) => {
      dispatch(setCode({ questionId, code: submittedCode }));
      client.send({
        type: MessageType.SUBMISSION,
        subtype: SubmissionType.PROGRAMMING_SUBMISSION,
        payload: { questionId, submittedCode },
      });
    },
    [dispatch]
  );

  const handleQuestionNavigation = useCallback(
    (index: number) => {
      const previousQuestionId =
        examState.examQuestions[examState.currentQuestionIndex].question.id;
      const timeSpent = Math.floor(
        (Date.now() - (examState.questionStartTimes[previousQuestionId] || Date.now())) / 1000
      );

      dispatch(navigateQuestion(index));

      // Send the time spent on the previous question to the server
      if (timeSpent > 0) {
        client.send({
          type: MessageType.SUBMISSION,
          subtype: SubmissionType.UPDATE_TIME_SPENT,
          payload: { questionId: previousQuestionId, timeSpent },
        });
      }
    },
    [
      dispatch,
      examState.currentQuestionIndex,
      examState.examQuestions,
      examState.questionStartTimes,
    ]
  );

  const handleExamSubmit = useCallback(() => {
    // Send the time spent on the last un-navigated question to the server
    const previousQuestionId = examState.examQuestions[examState.currentQuestionIndex].question.id;
    const timeSpent = Math.floor(
      (Date.now() - (examState.questionStartTimes[previousQuestionId] || Date.now())) / 1000
    );
    client.send({
      type: MessageType.SUBMISSION,
      subtype: SubmissionType.UPDATE_TIME_SPENT,
      payload: { questionId: previousQuestionId, timeSpent },
    });

    client.send({
      type: MessageType.ACTION,
      subtype: ActionType.SUBMIT_EXAM_REQ,
      payload: {},
    });
  }, [dispatch]);

  const handleStartExam = useCallback(() => {
    dispatch(startExam());
  }, [dispatch]);

  const handleNext = useCallback(() => {
    if (examState.currentQuestionIndex < examState.examQuestions.length - 1) {
      handleQuestionNavigation(examState.currentQuestionIndex + 1);
    }
  }, [examState.examQuestions, examState.currentQuestionIndex, handleQuestionNavigation]);

  const handlePrevious = useCallback(() => {
    if (examState.currentQuestionIndex > 0) {
      handleQuestionNavigation(examState.currentQuestionIndex - 1);
    }
  }, [examState.examQuestions, examState.currentQuestionIndex, handleQuestionNavigation]);

  const currentQuestion = (
    examState.examQuestions.length ? examState.examQuestions[examState.currentQuestionIndex] : null
  ) as ActiveExamQuestion;

  return {
    examState,
    currentQuestion,
    questions: examState.examQuestions,
    handleAnswerChange,
    handleCodeChange,
    handleQuestionNavigation,
    handleExamSubmit,
    handleStartExam,
    handleNext,
    handlePrevious,
  };
};

export default useActiveExam;
