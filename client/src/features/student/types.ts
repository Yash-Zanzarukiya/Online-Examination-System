import { UUID } from "crypto";
import { ApiResponse } from "@/types/ApiResponse";

export interface StudentProfileState {
  profile: StudentProfileData | null;
  isLoading: boolean;
}

export interface StudentProfileData {
  id: UUID;
  userId: UUID;
  fullName: string;
  college: {
    id: UUID;
    name: string;
  };
  branch: string;
  phone: string;
  passout: number;
  createdAt: any;
  updatedAt: any;
}

export interface StudentProfileDTO {
  userId: UUID;
  fullName: string;
  collegeId: UUID;
  branch: string;
  phone: string;
  passout: number;
}

export type StudentProfileResponse = ApiResponse<{ data: StudentProfileData }>;

export type StudentProfileListResponse = ApiResponse<StudentProfileData[]>;
