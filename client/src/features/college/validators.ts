import { z } from "zod";

export const collegeSchema = z.object({
  name: z.string().min(2, "College name must be at least 2 characters long").max(100, "College name must not exceed 100 characters"),
});

export type CollegeFormData = z.infer<typeof collegeSchema>;

