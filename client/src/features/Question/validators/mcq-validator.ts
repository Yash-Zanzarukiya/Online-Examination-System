import { z } from "zod";
import { QuestionFormSchema } from "./question-validator";

export const McqOptionFormSchema = z.object({
  id: z.string().uuid().optional(),
  questionId: z.string().uuid().optional(),
  optionText: z.string().min(1, "Option text must be at least 1 character long"),
  imageUrl: z.string().optional(),
  imageFile: z.any().optional(),
  isCorrect: z.boolean(),
});

export const McqQuestionFormSchema = z.object({
  question: QuestionFormSchema,
  options: z.array(McqOptionFormSchema).min(2, "At least two options are required"),
});

export type McqOptionForm = z.infer<typeof McqOptionFormSchema>;
export type McqQuestionForm = z.infer<typeof McqQuestionFormSchema>;
