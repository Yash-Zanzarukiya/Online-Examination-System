import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getAllQuestions } from "../redux/questionThunks";

function useLibraryQuestions() {
  const dispatch = useAppDispatch();
  const { questions } = useAppSelector(({ questionLibrary }) => questionLibrary);

  useEffect(() => {
    dispatch(getAllQuestions());
  }, [dispatch]);

  return { questions };
}

export default useLibraryQuestions;
