import { z } from "zod";
import { useForm } from "react-hook-form";
import { examSchema } from "../validators";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAppDispatch } from "@/app/hooks";
import { createExam, updateExam } from "../redux/examThunks";
import { navigateTo } from "@/utils";
import { Exam } from "../types";

type ExamSchema = z.infer<typeof examSchema>;

export default function useExamForm(initialValues?: Exam) {
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
    if (initialValues) {
      await dispatch(updateExam({ examId: initialValues.id, examData: data }));
    } else {
      await dispatch(createExam(data));
      navigateTo("/admin/exams");
    }
  };

  return { form, onSubmit };
}
