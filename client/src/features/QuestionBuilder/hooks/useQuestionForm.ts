import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAppDispatch } from "@/app/hooks";
import { QuestionCreatePayloadSchema } from "../validators";
import {
  createFullQuestionThunk,
  updateQuestionThunk,
  deleteQuestionThunk,
  getFullQuestionByIdThunk,
} from "../redux/questionThunks";
import {
  createMcqOptionThunk,
  updateMcqOptionThunk,
  deleteMcqOptionThunk,
} from "../redux/optionThunks";
import { FullQuestion, QuestionCreatePayload } from "../types";
import { UUID } from "crypto";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";

export const useQuestionForm = (initialData?: FullQuestion) => {
  const dispatch = useAppDispatch();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const form = useForm<QuestionCreatePayload>({
    resolver: zodResolver(QuestionCreatePayloadSchema),
    defaultValues: initialData
      ? {
          question: {
            id: initialData.question.id,
            categoryId: initialData.question.category?.id,
            difficulty: initialData.question.difficulty,
            type: initialData.question.type,
            questionText: initialData.question.questionText,
          },
          options: initialData.options?.map((option) => ({
            id: option.id,
            questionId: option.questionId,
            optionText: option.optionText,
            isCorrect: option.correct,
          })) || [
            { optionText: "", isCorrect: true },
            { optionText: "", isCorrect: false },
          ],
        }
      : {
          question: {
            difficulty: Difficulty.EASY,
            type: QuestionType.MCQ,
            questionText: "",
          },
          options: [
            { optionText: "", isCorrect: true },
            { optionText: "", isCorrect: false },
          ],
        },
  });

  const handleSubmit = async (data: QuestionCreatePayload) => {
    setIsSubmitting(true);
    try {
      if (data.question.id) {
        await updateFullQuestion(data);
      } else {
        await dispatch(createFullQuestionThunk(data));
      }
    } catch (error) {
      console.error("Error submitting question:", error);
    }
    setIsSubmitting(false);
  };

  const updateFullQuestion = async (data: QuestionCreatePayload) => {
    if (!data.question.id) return;
    await dispatch(
      updateQuestionThunk({ questionId: data.question.id, questionData: data.question })
    );
    for (const option of data.options) {
      if (option.id) {
        await dispatch(updateMcqOptionThunk({ optionId: option.id, optionData: option }));
      } else {
        await dispatch(createMcqOptionThunk({ ...option, questionId: data.question.id }));
      }
    }
  };

  const handleUpdateQuestion = async (data: QuestionCreatePayload) => {
    if (data.question.id) {
      setIsSubmitting(true);
      await dispatch(
        updateQuestionThunk({ questionId: data.question.id, questionData: data.question })
      );
      setIsSubmitting(false);
    }
  };

  const handleDeleteQuestion = async (questionId: UUID | undefined) => {
    setIsSubmitting(true);
    if (questionId) await dispatch(deleteQuestionThunk(questionId));
    form.reset();
    setIsSubmitting(false);
  };

  const loadQuestion = async (questionId: UUID) => {
    const questionData = await dispatch(getFullQuestionByIdThunk(questionId)).unwrap();
    if (questionData) form.reset(questionData);
  };

  const handleAddOption = () => {
    const options = form.getValues("options");
    form.setValue("options", [...options, { optionText: "", isCorrect: false }]);
  };

  const handleDeleteOption = async (index: number) => {
    const options = form.getValues("options");
    if (options.length <= 2) return;

    const optionToDelete = options[index];
    if (optionToDelete.id) {
      await dispatch(deleteMcqOptionThunk(optionToDelete.id));
    }

    form.setValue(
      "options",
      options.filter((_, i) => i !== index)
    );
  };

  return {
    form,
    isSubmitting,
    handleSubmit,
    handleAddOption,
    handleDeleteOption,
    handleUpdateQuestion,
    handleDeleteQuestion,
    loadQuestion,
  };
};
