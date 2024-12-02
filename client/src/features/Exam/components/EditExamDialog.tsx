import { ExamForm } from "../components";
import { useAppSelector } from "@/app/hooks";
import { useExamForm } from "../hooks";
import { Exam } from "../types";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";

interface EditExamDialogProps {
  initialData: Exam;
  isOpen: boolean;
  onClose: () => void;
}

function EditExamDialog({ initialData, isOpen, onClose }: EditExamDialogProps) {
  const { isLoading } = useAppSelector(({ exams }) => exams);
  const { form, onSubmit } = useExamForm(initialData);

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Edit Exam</DialogTitle>
        </DialogHeader>
        <ExamForm form={form} onSubmit={onSubmit} isLoading={isLoading} isUpdating />
      </DialogContent>
    </Dialog>
  );
}

export default EditExamDialog;
