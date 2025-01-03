import { CandidateState, ExamAttemptStatus } from "@/features/ExamCandidates/types";
import { CandidateHeader, ExamActivityTab, PerformanceTab } from "../components";
import { useCallback, useEffect } from "react";
import { useAppDispatch } from "@/app/hooks";
import { updateCandidateStatus } from "../redux/candidateReportThunks";
import { setCandidateState } from "../redux/candidateReportSlice";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

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
      <Tabs defaultValue="performance">
        <TabsList className="ml-6 mt-4">
          <TabsTrigger value="performance">Performance</TabsTrigger>
          <TabsTrigger value="activity">Activity</TabsTrigger>
        </TabsList>
        <TabsContent value="performance">
          <PerformanceTab />
        </TabsContent>
        <TabsContent value="activity">
          <ExamActivityTab />
        </TabsContent>
      </Tabs>
    </div>
  );
}
