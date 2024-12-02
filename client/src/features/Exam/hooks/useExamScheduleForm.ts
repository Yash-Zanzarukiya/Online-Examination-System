import { ExamScheduleForm as examScheduleForm, examScheduleSchema } from "../validators";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { updateExamSchedule } from "../redux/examThunks";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { UUID } from "crypto";
import { useEffect } from "react";

const useExamScheduleForm = (examId: UUID, startDate?: Date | null) => {
  const dispatch = useAppDispatch();

  const { isLoading } = useAppSelector(({ exams }) => exams);

  const form = useForm<examScheduleForm>({
    resolver: zodResolver(examScheduleSchema),
    defaultValues: {
      startDate: startDate ?? undefined,
    },
  });

  useEffect(() => {
    form.reset({ startDate: startDate ?? undefined });
  }, [startDate, examId]);

  function onSubmit(values: examScheduleForm) {
    dispatch(updateExamSchedule({ examId, examData: values }));
  }

  return { form, onSubmit, isLoading };
};

export default useExamScheduleForm;
