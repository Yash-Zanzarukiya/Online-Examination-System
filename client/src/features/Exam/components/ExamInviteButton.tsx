import { useAppDispatch } from "@/app/hooks";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import ExamInviteDialog from "./ExamInviteDialog";
import { inviteCandidates } from "../redux/examThunks";
import { UUID } from "crypto";

export default function ExamInviteButton({ scheduledExamId }: { scheduledExamId: UUID }) {
  const dispatch = useAppDispatch();
  const [isImportDialogOpen, setIsImportDialogOpen] = useState(false);

  const handleImport = (file: File) => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      dispatch(inviteCandidates({ scheduledExamId, formData }));
    }
  };

  return (
    <>
      <Button
        size="sm"
        className="bg-blue-600 hover:bg-blue-700"
        onClick={() => setIsImportDialogOpen(true)}
      >
        Invite
      </Button>
      <ExamInviteDialog
        isOpen={isImportDialogOpen}
        onClose={() => setIsImportDialogOpen(false)}
        onImport={handleImport}
      />
    </>
  );
}
