import { z } from "zod";

export const examSchema = z.object({
  title: z
    .string()
    .min(3, "Title must be at least 3 characters long")
    .max(255, "Title must be at most 255 characters long"),
  passingScore: z.number().int().min(0, "Passing score must be at least 0"),
  timeLimit: z.number().int().min(1).max(300, "Time limit must be between 1 and 300 minutes"),
});
