import { UUID } from "crypto";
import { CollegeDTO } from "../college/types";

export interface StudentProfileState {
  students: StudentData[];
  profile: StudentProfileData | null;
  isLoading: boolean;
}

export interface StudentProfileData {
  id: UUID;
  userId: UUID;
  fullName: string;
  college: CollegeDTO;
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

export interface StudentData {
  userId: UUID;
  studentProfileId: UUID;
  email: string;
  username: string;
  fullName: string;
  college: CollegeDTO;
  branch: string;
  phone: string;
  passout: number;
}

export interface StudentDataFilters {
  collegeId?: UUID;
  passout?: number;
}
