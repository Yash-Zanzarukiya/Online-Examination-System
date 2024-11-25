import { z } from "zod";
import { useState } from "react";
import { Roles } from "@/types/Roles";
import { useForm } from "react-hook-form";
import { useAppDispatch } from "@/app/hooks";
import { signUpSchema } from "../validators";
import { signUpThunk } from "../redux/authThunks";
import { zodResolver } from "@hookform/resolvers/zod";

export function useSignUpForm() {
  const dispatch = useAppDispatch();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const form = useForm<z.infer<typeof signUpSchema>>({
    defaultValues: {
      username: "",
      email: "",
      password: "",
      fullName: "",
    },
    resolver: zodResolver(signUpSchema),
  });

  const handleSignUp = async (data: z.infer<typeof signUpSchema>) => {
    setIsSubmitting(true);
    await dispatch(signUpThunk({ ...data, role: Roles.STUDENT }));
    setIsSubmitting(false);
  };

  return { form, isSubmitting, handleSignUp };
}
