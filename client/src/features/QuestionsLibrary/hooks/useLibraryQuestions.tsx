import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useCallback, useEffect } from "react";
import { getAllQuestions } from "../redux/questionThunks";

function useLibraryQuestions() {
  const dispatch = useAppDispatch();
  const { questions } = useAppSelector(({ questionLibrary }) => questionLibrary);

  const fetchQuestions = useCallback(() => {
    dispatch(getAllQuestions());
  }, [dispatch]);

  useEffect(() => {
    fetchQuestions();
  }, [dispatch]);

  return { questions, reFetch: fetchQuestions };
}

export default useLibraryQuestions;
