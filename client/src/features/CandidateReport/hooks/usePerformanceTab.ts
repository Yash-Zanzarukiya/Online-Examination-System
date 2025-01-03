import { useAppDispatch, useAppSelector } from "@/app/hooks";
import {
  getCandidateQuestionsAnalysis,
  getCandidateScoreDistribution,
} from "../redux/candidateReportThunks";
import { useEffect } from "react";

function usePerformanceTab() {
  const dispatch = useAppDispatch();

  const { questionsAnalysis, scoreDistribution, candidateState } = useAppSelector(
    ({ candidateReport }) => candidateReport
  );

  useEffect(() => {
    const examAttemptId = candidateState?.examAttemptId;
    if (examAttemptId) {
      dispatch(getCandidateScoreDistribution(examAttemptId));
      dispatch(getCandidateQuestionsAnalysis(examAttemptId));
    }
  }, [dispatch, candidateState]);

  return { questionsAnalysis, scoreDistribution };
}

export default usePerformanceTab;
