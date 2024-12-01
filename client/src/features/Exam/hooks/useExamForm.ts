import { z } from "zod";
import { useForm } from "react-hook-form";
import { examSchema } from "../validators";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAppDispatch } from "@/app/hooks";
import { createExam } from "../redux/examThunks";
import { navigateTo } from "@/utils";

type ExamSchema = z.infer<typeof examSchema>;

export default function useExamForm(initialValues?: ExamSchema) {
  const dispatch = useAppDispatch();

  const form = useForm<ExamSchema>({
    resolver: zodResolver(examSchema),
    defaultValues: initialValues || {
      title: "",
      passingScore: 0,
      timeLimit: 0,
    },
  });

  const onSubmit = async (data: ExamSchema) => {
    await dispatch(createExam(data));
    navigateTo("/admin/exams");
  };

  return { form, onSubmit };
}
