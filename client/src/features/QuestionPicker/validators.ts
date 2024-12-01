import { z } from "zod";

export const questionPickerFormSchema = z.object({
  examId: z.string().uuid(),
  questionIds: z.array(z.string().uuid()),
});
