import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { StudentProfileDTO } from "../types";
import { studentProfileSchema } from "../validators";
import { UUID } from "crypto";
import { createStudentProfileThunk, updateStudentProfileThunk } from "../redux/studentThunks";
import { useEffect } from "react";

export function useStudentProfileForm(userId: UUID) {
  const dispatch = useAppDispatch();
  const { profile, isLoading } = useAppSelector(({ studentProfile }) => studentProfile);

  const form = useForm<StudentProfileDTO>({
    resolver: zodResolver(studentProfileSchema),
    defaultValues: { ...profile, collegeId: profile?.college.id } || {
      fullName: "",
      branch: "",
      phone: "",
      passout: new Date().getFullYear(),
    },
  });

  useEffect(() => {
    if (profile) {
      form.reset({
        ...profile,
        collegeId: profile.college.id,
      });
    }
  }, [profile]);

  const onSubmit = (data: StudentProfileDTO) => {
    if (profile) {
      dispatch(updateStudentProfileThunk({ profileId: profile.id, profile: { ...data, userId } }));
    } else {
      dispatch(createStudentProfileThunk({ ...data, userId }));
    }
  };

  return { form, onSubmit, isLoading };
}
