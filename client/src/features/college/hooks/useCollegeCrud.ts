import { useCallback } from "react";
import { CollegeDTO } from "../types";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import {
  createCollegeThunk,
  deleteCollegeThunk,
  editCollegeThunk,
  getAllCollegesThunk,
} from "../redux/collegeThunks";
import { UUID } from "crypto";

export function useCollegeCrud() {
  const dispatch = useAppDispatch();

  const { colleges, isLoading } = useAppSelector(({ college }) => college);

  const loadColleges = useCallback(async () => {
    dispatch(getAllCollegesThunk());
  }, []);

  const createCollege = useCallback(async (college: Omit<CollegeDTO, "id">) => {
    dispatch(createCollegeThunk(college));
  }, []);

  const editCollege = useCallback(async (collegeId: UUID, college: Partial<CollegeDTO>) => {
    dispatch(editCollegeThunk({ collegeId, college }));
  }, []);

  const removeCollege = useCallback(async (collegeId: UUID) => {
    dispatch(deleteCollegeThunk(collegeId));
  }, []);

  return { colleges, loading: isLoading, loadColleges, createCollege, editCollege, removeCollege };
}
