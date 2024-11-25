import { z } from "zod";

export const signInSchema = z.object({
  identifier: z.string().min(3, "requires at least 3 characters"),
  password: z
    .string()
    .min(8, "password must have at least 8 characters")
    .max(16, "password must not have more than 16 characters"),
});

export const usernameValidation = z
  .string()
  .min(3, "Username must have at least 3 characters")
  .max(20, "Username must not have more than 20 characters")
  .regex(/^[a-zA-Z0-9_]+$/, "Username must not contain special characters");

export const signUpSchema = z.object({
  username: usernameValidation,
  email: z.string().email("Invalid email address formate"),
  fullName: z.string().min(3, "Full name must have at least 3 characters"),
  password: z
    .string()
    .min(6, "Password must have at least 6 characters")
    .max(16, "Password must not have more than 16 characters"),
});
