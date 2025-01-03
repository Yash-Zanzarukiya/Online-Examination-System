import { CandidateDrawer, CandidatesTable } from "../components";
import { useParams } from "react-router-dom";
import { UUID } from "crypto";
import { useCandidateDrawer, useExamCandidates } from "../hooks";

function ExamCandidates() {
  const { scheduledExamId } = useParams();

  const { isDrawerOpen, selectedCandidate, handleRowClick, handleCloseDrawer } =
    useCandidateDrawer();

  const { candidates, reFetch } = useExamCandidates(scheduledExamId as UUID);

  return (
    <>
      <CandidatesTable data={candidates} onRowClick={handleRowClick} onRefetch={reFetch} />
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
