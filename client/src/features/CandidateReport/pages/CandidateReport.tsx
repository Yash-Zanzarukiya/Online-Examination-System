import { CandidateState, ExamAttemptStatus } from "@/features/ExamCandidates/types";
import { CandidateHeader, PerformanceTab } from "../components";
import { useCallback, useEffect } from "react";
import { useAppDispatch } from "@/app/hooks";
import { updateCandidateStatus } from "../redux/candidateReportThunks";
import { setCandidateState } from "../redux/candidateReportSlice";

interface CandidateReportProps {
  candidateState: CandidateState;
}

export default function CandidateReport({ candidateState }: CandidateReportProps) {
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (candidateState) dispatch(setCandidateState(candidateState));
  }, [candidateState]);

  const handleStatusChange = useCallback(
    (status: ExamAttemptStatus) => {
      dispatch(updateCandidateStatus({ examAttemptId: candidateState.examAttemptId, status }));
    },
    [candidateState.examAttemptId]
  );

  return (
    <div className="h-full w-full">
      <CandidateHeader candidateState={candidateState} handleStatusChange={handleStatusChange} />
      <PerformanceTab />
    </div>
  );
}
