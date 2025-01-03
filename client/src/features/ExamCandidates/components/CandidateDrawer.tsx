import { Drawer, DrawerContent } from "@/components/ui/drawer";
import CandidateReport from "@/features/CandidateReport/pages/CandidateReport";
import { CandidateState } from "../types";
import { ScrollArea } from "@/components/ui/scroll-area";

interface CandidateDrawerProps {
  isDrawerOpen: boolean;
  handleCloseDrawer: () => void;
  selectedCandidate: CandidateState;
}

function CandidateDrawer({
  isDrawerOpen,
  handleCloseDrawer,
  selectedCandidate,
}: CandidateDrawerProps) {
  return (
    <Drawer open={isDrawerOpen} onOpenChange={handleCloseDrawer} direction="left">
      <DrawerContent className="w-1/2 h-screen">
        <ScrollArea>
          <CandidateReport candidateState={selectedCandidate} />
        </ScrollArea>
      </DrawerContent>
    </Drawer>
  );
}

export default CandidateDrawer;
