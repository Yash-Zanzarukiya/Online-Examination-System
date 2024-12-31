import { useEffect } from "react";
import { CandidateDrawer, CandidatesTable } from "../components";
import { fetchCandidates } from "../redux/examCandidatesThunks";
import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useParams } from "react-router-dom";
import { UUID } from "crypto";
import { useCandidateDrawer } from "../hooks";

function ExamCandidates() {
  const dispatch = useAppDispatch();
  const { scheduledExamId } = useParams();

  const { isDrawerOpen, selectedCandidate, handleRowClick, handleCloseDrawer } =
    useCandidateDrawer();

  const { candidates, isLoading } = useAppSelector(({ examCandidates }) => examCandidates);

  useEffect(() => {
    if (scheduledExamId) {
      dispatch(fetchCandidates(scheduledExamId as UUID));
    }
  }, [scheduledExamId]);

  if (isLoading) return <div>Loading...</div>;

  return (
    <>
      <CandidatesTable data={candidates} onRowClick={handleRowClick} />
      {selectedCandidate && (
        <CandidateDrawer
          isDrawerOpen={isDrawerOpen}
          handleCloseDrawer={handleCloseDrawer}
          selectedCandidate={selectedCandidate}
        />
      )}
    </>
  );
}

export default ExamCandidates;
