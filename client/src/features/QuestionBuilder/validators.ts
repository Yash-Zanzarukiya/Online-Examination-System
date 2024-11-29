import { z } from "zod";

export const QuestionFormSchema = z.object({
  id: z.string().uuid().optional(),
  categoryId: z.string().uuid().optional(),
  difficulty: z.string(),
  type: z.string(),
  questionText: z.string().trim().min(5, "Question text must be at least 5 characters long"),
  imageUrl: z.string().optional(),
  imageFile: z.any().optional(),
  correctAnswerId: z.string().optional(),
});

export const McqOptionFormSchema = z.object({
  id: z.string().uuid().optional(),
  questionId: z.string().uuid().optional(),
  optionText: z.string().min(1, "Option text must be at least 1 character long"),
  imageUrl: z.string().optional(),
  imageFile: z.any().optional(),
  isCorrect: z.boolean(),
});

export const QuestionCreatePayloadSchema = z.object({
  question: QuestionFormSchema,
  options: z.array(McqOptionFormSchema),
});
