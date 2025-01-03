import { ExamScheduleForm as examScheduleForm, examScheduleSchema } from "../validators";
import { useAppDispatch } from "@/app/hooks";
import { scheduleExam, updateExamSchedule } from "../redux/examThunks";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { UUID } from "crypto";
import { useEffect } from "react";
import { ScheduledExam } from "../types";
import { format } from "date-fns";

const getFormFields = (initialData?: ScheduledExam) => {
  return {
    name: initialData?.name || "",
    startingAt: initialData?.startingAt ? new Date(initialData.startingAt) : undefined,
    collegeId: initialData?.collegeId || "",
  };
};

const useExamScheduleForm = (examId: UUID, initialData?: ScheduledExam) => {
  const dispatch = useAppDispatch();

  const form = useForm<examScheduleForm>({
    resolver: zodResolver(examScheduleSchema),
    defaultValues: getFormFields(initialData),
  });

  useEffect(() => {
    if (initialData) form.reset(getFormFields(initialData));
  }, [initialData]);

  function onSubmit(examData: examScheduleForm) {
    const scheduledExamId = initialData?.id;
    const startingAt: Date = format(examData.startingAt, "yyyy-MM-dd'T'HH:mm");
    if (scheduledExamId) {
      dispatch(
        updateExamSchedule({
          scheduledExamId: initialData?.id,
          examData: { ...examData, startingAt },
        })
      );
    } else {
      dispatch(scheduleExam({ examId, examData }));
    }
  }

  return { form, onSubmit };
};

export default useExamScheduleForm;
