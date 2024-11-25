import { z } from "zod";
import { signInSchema, usernameValidation } from "../validators";
import { axiosInstance } from "@/utils";
import {
  checkUsernameResponse,
  getCurrentUserResponse,
  ISignUpDTO,
  logoutResponse,
  signInResponse,
  signUpResponse,
} from "../types";

export const SignUp = async (signUpDTO: ISignUpDTO): Promise<signUpResponse> => {
  return await axiosInstance.post("/auth/register", signUpDTO);
};

export const SignIn = async (loginDTO: z.infer<typeof signInSchema>): Promise<signInResponse> => {
  return await axiosInstance.post("/auth/login", loginDTO);
};

export const logout = async (): Promise<logoutResponse> => {
  return await axiosInstance.post("/auth/logout");
};

export const getCurrentUser = async (): Promise<getCurrentUserResponse> => {
  return await axiosInstance.get("/auth/me");
};

export const checkUniqueUsername = async (
  username: z.infer<typeof usernameValidation>
): Promise<checkUsernameResponse> => {
  const apiRes = await axiosInstance.get(`/auth/check/username/${username}`);
  return apiRes.data;
};
