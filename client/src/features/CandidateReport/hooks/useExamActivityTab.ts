import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getExamActivities } from "../redux/candidateReportThunks";

function useExamActivityTab() {
  const dispatch = useAppDispatch();
  const { candidateState, examActivities, isLoading } = useAppSelector(
    ({ candidateReport }) => candidateReport
  );

  useEffect(() => {
    const examAttemptId = candidateState?.examAttemptId;
    if (examAttemptId) {
      dispatch(getExamActivities(examAttemptId));
    }
  }, [dispatch, candidateState?.examAttemptId]);

  return { examActivities, isLoading };
}

export default useExamActivityTab;
