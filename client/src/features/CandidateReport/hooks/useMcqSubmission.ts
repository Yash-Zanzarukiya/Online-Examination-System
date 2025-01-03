import { useAppSelector } from "@/app/hooks";
import { UUID } from "crypto";
import { useEffect, useState } from "react";
import { McqSubmission } from "../types";
import candidateReportApi from "../api/candidateReportApi";
import { toastApiError } from "@/utils";

function useMcqSubmission(questionId: UUID) {
  const [mcqSubmission, setMcqSubmission] = useState<McqSubmission | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const examAttemptId = useAppSelector(
    ({ candidateReport }) => candidateReport.candidateState?.examAttemptId
  );

  useEffect(() => {
    const fetchMcqSubmission = async (examAttemptId: UUID, questionId: UUID) => {
      try {
        const apiRes = await candidateReportApi.getMcqSubmission(examAttemptId, questionId);
        console.log("apiRes: ", apiRes);
        setMcqSubmission(apiRes.data.data);
      } catch (error) {
        toastApiError("Failed to fetch MCQ submission", error);
      } finally {
        setIsLoading(false);
      }
    };
    console.log(examAttemptId + " " + questionId);

    if (examAttemptId && questionId) {
      setIsLoading(true);
      fetchMcqSubmission(examAttemptId, questionId);
    }
  }, [examAttemptId, questionId]);

  return { mcqSubmission, isLoading };
}

export default useMcqSubmission;
