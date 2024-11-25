import { z } from "zod";

export const studentProfileSchema = z.object({
  fullName: z.string().min(3).max(100),
  collegeId: z.string().uuid(),
  branch: z.string().min(2).max(50),
  phone: z.string().min(10).max(12),
  passout: z.number().int().min(1950),
});
