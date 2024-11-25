import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { getStudentProfileThunk } from "../redux/studentThunks";
import { UUID } from "crypto";
import { useEffect, useState } from "react";

export const useStudentProfile = (userId: UUID | undefined) => {
  const dispatch = useAppDispatch();
  const [isLoading, setIsLoading] = useState(false);
  const profile = useAppSelector(({ studentProfile }) => studentProfile.profile);

  useEffect(() => {
    async function fetchProfile(userId: UUID) {
      await dispatch(getStudentProfileThunk(userId));
    }

    if (!profile && userId) {
      setIsLoading(true);
      fetchProfile(userId);
      setIsLoading(false);
    }
  }, [userId]);

  return { profile, isLoading };
};
