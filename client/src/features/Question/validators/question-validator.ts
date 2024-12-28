import { z } from "zod";

export const QuestionFormSchema = z.object({
  id: z.string().uuid().optional(),
  categoryId: z.string().uuid().optional(),
  difficulty: z.string(),
  type: z.string(),
  questionText: z.string().trim().min(5, "Question text must be at least 5 characters long"),
  marks: z.number().int().min(1, "Marks must be at least 1"),
  imageUrl: z.string().optional(),
  imageFile: z.any().optional(),
});

export type QuestionForm = z.infer<typeof QuestionFormSchema>;
