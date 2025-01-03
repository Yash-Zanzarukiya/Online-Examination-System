import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { fetchCandidates } from "../redux/examCandidatesThunks";
import { useCallback, useEffect } from "react";
import { UUID } from "crypto";

function useExamCandidates(scheduledExamId: UUID) {
  const dispatch = useAppDispatch();
  const { candidates, isLoading } = useAppSelector(({ examCandidates }) => examCandidates);

  const fetchExamCandidates = useCallback(() => {
    if (scheduledExamId) {
      dispatch(fetchCandidates(scheduledExamId));
    }
  }, [scheduledExamId]);

  useEffect(() => {
    fetchExamCandidates();
  }, [scheduledExamId]);

  return {
    candidates,
    isLoading,
    reFetch: fetchExamCandidates,
  };
}

export default useExamCandidates;
