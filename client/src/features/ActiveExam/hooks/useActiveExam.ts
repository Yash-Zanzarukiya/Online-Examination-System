import { useEffect, useCallback, useRef } from "react";
import {
  navigateQuestion,
  setAnswer,
  setCode,
  decrementTime,
  startExam,
} from "../redux/activeExamSlice";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { ActiveExamQuestion } from "../types";
import { UUID } from "crypto";
import { useWebSocket } from "@/hooks";
import {
  ActionType,
  ExamEventType,
  MessageType,
  SubmissionType,
} from "@/features/ExamWebSocket/types/message-types";

let timer: NodeJS.Timeout | null = null;
let pingInterval: NodeJS.Timeout | null = null;

export const useActiveExam = () => {
  const client = useWebSocket();
  const dispatch = useAppDispatch();
  const examState = useAppSelector(({ activeExam }) => activeExam);
  const timeRemainingRef = useRef(examState.timeRemaining);

  useEffect(() => {
    timeRemainingRef.current = examState.timeRemaining;
  }, [examState.timeRemaining]);

  // Ping logic
  useEffect(() => {
    if (examState.isExamStarted && !examState.isExamSubmitted) {
      if (!pingInterval) {
        pingInterval = setInterval(() => {
          client.send({
            type: MessageType.ACTION,
            subtype: ActionType.PING,
            payload: timeRemainingRef.current,
          });
        }, 15000);
      }
    }

    client.send({
      type: MessageType.ExamEvent,
      subtype: ExamEventType.TAB_SWITCH,
      payload: {},
    });
    client.send({
      type: MessageType.ExamEvent,
      subtype: ExamEventType.COPY,
      payload: {},
    });
    client.send({
      type: MessageType.ExamEvent,
      subtype: ExamEventType.PASTE,
      payload: {},
    });

    return () => {
      if (pingInterval) {
        clearInterval(pingInterval);
        pingInterval = null;
      }
    };
  }, [examState.isExamStarted, examState.isExamSubmitted, client]);

  // Timer logic
  useEffect(() => {
    if (examState.isExamStarted && !examState.isExamSubmitted) {
      if (timer) clearInterval(timer);
      timer = setInterval(() => {
        dispatch(decrementTime());
      }, 1000);
    }

    return () => {
      if (timer) {
        clearInterval(timer);
        timer = null;
      }
    };
  }, [examState.isExamStarted, examState.isExamSubmitted, dispatch]);

  // auto-Submit the exam when the time runs out
  useEffect(() => {
    if (examState.timeRemaining === 0) {
      handleExamSubmit();
    }
  }, [examState.timeRemaining, dispatch]);

  // Handle the change in multiple choice question answer
  const handleAnswerChange = useCallback(
    (questionId: UUID, selectedOptionId: UUID) => {
      dispatch(setAnswer({ questionId, selectedOptionId }));
      client.send({
        type: MessageType.SUBMISSION,
        subtype: SubmissionType.MULTIPLE_CHOICE_SUBMISSION,
        payload: {
          questionId,
          selectedOptionId,
          questionNumber: examState.currentQuestionIndex + 1,
        },
      });
    },
    [dispatch, examState.currentQuestionIndex]
  );

  // Handle the change in the programming question code
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

  // Handle the navigation between questions and send the time spent on the question to the server
  const handleQuestionNavigation = useCallback(
    (index: number) => {
      const previousQuestionId =
        examState.examQuestions[examState.currentQuestionIndex].question.id;
      const timeSpent =
        Date.now() - (examState.questionStartTimes[previousQuestionId] || Date.now());

      dispatch(navigateQuestion(index));

      client.send({
        type: MessageType.ExamEvent,
        subtype: ExamEventType.QUESTION_NAVIGATION,
        payload: "Viewed question " + (index + 1),
      });

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

  // Handle the exam submission
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

  // Handle the exam start
  const handleStartExam = useCallback(() => {
    dispatch(startExam());
  }, [dispatch]);

  // Handle the navigation to the next question
  const handleNext = useCallback(() => {
    if (examState.currentQuestionIndex < examState.examQuestions.length - 1) {
      handleQuestionNavigation(examState.currentQuestionIndex + 1);
    }
  }, [examState.examQuestions, examState.currentQuestionIndex, handleQuestionNavigation]);

  // Handle the navigation to the previous question
  const handlePrevious = useCallback(() => {
    if (examState.currentQuestionIndex > 0) {
      handleQuestionNavigation(examState.currentQuestionIndex - 1);
    }
  }, [examState.examQuestions, examState.currentQuestionIndex, handleQuestionNavigation]);

  // Holds current question and null if there is no question
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
