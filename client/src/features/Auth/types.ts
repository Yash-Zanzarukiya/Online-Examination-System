import { ApiResponse } from "@/types/ApiResponse";
import { Roles } from "@/types/Roles";
import { UUID } from "crypto";

export interface AuthState {
  isLoading: boolean;
  status: boolean;
  authData: AuthData | null;
}

export interface AuthData {
  id: UUID;
  userId: UUID;
  username: string;
  email: string;
  role: Roles;
  fullName: string;
}

export type signInResponse = ApiResponse<{ data: AuthData }>;

export type logoutResponse = ApiResponse<null>;

export type getCurrentUserResponse = ApiResponse<{ data: AuthData }>;

export interface ISignUpDTO {
  username: string;
  email: string;
  password: string;
  fullName: string;
  role: Roles;
}

export type signUpResponse = ApiResponse<null>;

export type checkUsernameResponse = ApiResponse<boolean>;
