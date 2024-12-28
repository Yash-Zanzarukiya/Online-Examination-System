import { z } from "zod";
import { QuestionFormSchema } from "./question-validator";

export const ProgrammingQuestionFormSchema = z.object({
  question: QuestionFormSchema,
  referenceAnswer: z.string().optional(),
});

export type ProgrammingQuestionForm = z.infer<typeof ProgrammingQuestionFormSchema>;
