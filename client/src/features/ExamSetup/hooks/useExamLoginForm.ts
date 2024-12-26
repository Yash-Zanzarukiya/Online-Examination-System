import { z } from "zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useParams } from "react-router-dom";
import { zodResolver } from "@hookform/resolvers/zod";
import { signInSchema } from "@/features/Auth/validators";
import examSetupApi from "@/features/ExamSetup/api/examSetupApi";
import { UUID } from "crypto";

export const useExamLoginForm = () => {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const { examId } = useParams();

  const form = useForm<z.infer<typeof signInSchema>>({
    resolver: zodResolver(signInSchema),
    defaultValues: { identifier: "yashpz", password: "12345678" },
  });

  const handleExamLogin = async (data: z.infer<typeof signInSchema>) => {
    setIsSubmitting(true);
    await examSetupApi.loginToExam({ ...data, scheduledExamId: examId as UUID });
    setIsSubmitting(false);
  };

  return { form, isSubmitting, handleExamLogin };
};
