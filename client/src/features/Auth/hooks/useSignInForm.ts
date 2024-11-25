import { z } from "zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useAppDispatch } from "@/app/hooks";
import { signInSchema } from "../validators";
import { loginThunk } from "../redux/authThunks";
import { zodResolver } from "@hookform/resolvers/zod";

export const useSignInForm = () => {
  const dispatch = useAppDispatch();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const form = useForm<z.infer<typeof signInSchema>>({
    resolver: zodResolver(signInSchema),
    defaultValues: { identifier: "johnwick", password: "12345678" },
  });

  const handleSignIn = async (data: z.infer<typeof signInSchema>) => {
    setIsSubmitting(true);
    await dispatch(loginThunk(data));
    setIsSubmitting(false);
  };

  return { form, isSubmitting, handleSignIn };
};
