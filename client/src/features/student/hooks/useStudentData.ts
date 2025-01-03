import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getAllStudentDataThunk } from "../redux/studentThunks";

function useStudentData() {
  const dispatch = useAppDispatch();
  const { isLoading, students } = useAppSelector(({ studentProfile }) => studentProfile);

  useEffect(() => {
    dispatch(getAllStudentDataThunk());
  }, [dispatch]);

  return { isLoading, students };
}

export default useStudentData;
