import { useAppSelector } from "@/app/hooks";
import { UUID } from "crypto";
import { useEffect, useState } from "react";
import { ProgrammingSubmission } from "../types";
import candidateReportApi from "../api/candidateReportApi";
import { toastApiError } from "@/utils";

function useProgrammingSubmission(questionId: UUID) {
  const [programmingSubmission, setProgrammingSubmission] = useState<ProgrammingSubmission | null>(
    null
  );
  const [isLoading, setIsLoading] = useState(true);

  const examAttemptId = useAppSelector(
    ({ candidateReport }) => candidateReport.candidateState?.examAttemptId
  );

  useEffect(() => {
    const fetchProgrammingSubmission = async (examAttemptId: UUID, questionId: UUID) => {
      try {
        const apiRes = await candidateReportApi.getProgrammingSubmission(examAttemptId, questionId);
        setProgrammingSubmission(apiRes.data.data);
      } catch (error) {
        toastApiError("Failed to fetch MCQ submission", error);
      } finally {
        setIsLoading(false);
      }
    };

    if (examAttemptId && questionId) {
      fetchProgrammingSubmission(examAttemptId, questionId);
    }
  }, [examAttemptId, questionId]);

  return { programmingSubmission, isLoading };
}

export default useProgrammingSubmission;
