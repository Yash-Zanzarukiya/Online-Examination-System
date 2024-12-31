import { useCallback, useState } from "react";
import { CandidateState } from "../types";

function useCandidateDrawer() {
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);
  const [selectedCandidate, setSelectedCandidate] = useState<CandidateState | null>(null);

  const handleRowClick = useCallback((candidateState: CandidateState) => {
    setSelectedCandidate(candidateState);
    setIsDrawerOpen(true);
  }, []);

  const handleCloseDrawer = useCallback(() => setIsDrawerOpen(false), []);

  return { isDrawerOpen, selectedCandidate, handleRowClick, handleCloseDrawer };
}

export default useCandidateDrawer;
