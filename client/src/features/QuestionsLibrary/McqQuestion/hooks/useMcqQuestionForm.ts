import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAppDispatch } from "@/app/hooks";
import { UUID } from "crypto";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { McqQuestion } from "@/features/Question/types/mcq-types";
import {
  McqQuestionForm,
  McqQuestionFormSchema,
} from "@/features/Question/validators/mcq-validator";
import { createMcqQuestion } from "@/features/QuestionsLibrary/redux/mcqQuestionThunks";
import { deleteQuestion, updateQuestion } from "@/features/QuestionsLibrary/redux/questionThunks";
import {
  createMcqOption,
  deleteMcqOption,
  updateMcqOption,
} from "@/features/QuestionsLibrary/redux/mcqOptionThunks";

let formData: McqQuestion | undefined = undefined;

const getStructuredFormData = (initialData?: McqQuestion): McqQuestionForm => {
  return initialData
    ? {
        question: {
          id: initialData.question.id,
          categoryId: initialData.question.category?.id,
          difficulty: initialData.question.difficulty,
          marks: initialData.question.marks,
          imageUrl: initialData.question.image,
          type: initialData.question.type,
          questionText: initialData.question.questionText,
        },
        options: initialData.options?.map((option) => ({
          id: option.id,
          questionId: option.questionId,
          imageUrl: option.image,
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
          marks: 1,
          questionText: "",
        },
        options: [
          { optionText: "", isCorrect: true },
          { optionText: "", isCorrect: false },
        ],
      };
};

export const useMcqQuestionForm = (initialData?: McqQuestion) => {
  const dispatch = useAppDispatch();

  const form = useForm<McqQuestionForm>({
    resolver: zodResolver(McqQuestionFormSchema),
    defaultValues: getStructuredFormData(initialData),
  });

  useEffect(() => {
    if (initialData && !formData) {
      formData = initialData;
      form.reset(getStructuredFormData(initialData));
    }
  }, [initialData]);

  const handleSubmit = (data: McqQuestionForm) => {
    console.log("data: ", data);
    if (initialData) {
      updateMcqQuestion(data);
    } else {
      dispatch(createMcqQuestion(data));
    }
  };

  const updateMcqQuestion = async (data: McqQuestionForm) => {
    if (!initialData) return;
    handleUpdateQuestion(data);
    handleOptions(data);
  };

  const handleUpdateQuestion = async (data: McqQuestionForm) => {
    if (!initialData) return;

    const questionId = initialData.question.id;
    const questionData = data.question;

    // if (initialData.question !== questionData)
    dispatch(updateQuestion({ questionId, questionData }));
  };

  const handleOptions = async (data: McqQuestionForm) => {
    if (!initialData) return;

    const questionId = initialData.question.id;

    for (const option of data.options) {
      if (option.id) {
        const mcqOptionId = option.id as UUID;
        dispatch(updateMcqOption({ mcqOptionId, data: option }));
      } else {
        dispatch(createMcqOption({ questionId, ...option }));
      }
    }
  };

  const handleDeleteQuestion = async () => {
    if (!initialData) return;
    const questionId = initialData.question.id;
    dispatch(deleteQuestion(questionId));
  };

  const handleAddOption = () => {
    const options = form.getValues("options");
    form.setValue("options", [...options, { optionText: "", isCorrect: false }]);
  };

  const handleUpdateOption = (index: number) => {
    const options = form.getValues("options");

    const option = options[index];
    if (!option.id) return;

    const mcqOptionId = option.id as UUID;

    dispatch(updateMcqOption({ mcqOptionId, data: option }));
  };

  const handleDeleteOption = async (index: number) => {
    const options = form.getValues("options");
    if (options.length <= 2) return;

    const optionToBeDelete = options[index];

    if (optionToBeDelete.id) {
      dispatch(deleteMcqOption(optionToBeDelete.id as UUID));
    }

    form.setValue(
      "options",
      options.filter((_, i) => i !== index)
    );
  };

  return {
    form,
    handleSubmit,
    handleUpdateQuestion,
    handleDeleteQuestion,
    handleAddOption,
    handleUpdateOption,
    handleDeleteOption,
  };
};
