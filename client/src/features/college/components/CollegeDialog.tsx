import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { CollegeForm } from "./CollegeForm";
import { CollegeDTO } from "../types";

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: { name: string }) => void;
  initialData?: CollegeDTO | null;
}

const CollegeDialog = ({ isOpen, onClose, onSubmit, initialData }: Props) => (
  <Dialog open={isOpen} onOpenChange={onClose}>
    <DialogContent>
      <DialogHeader>
        <DialogTitle>{initialData ? "Edit College" : "Add New College"}</DialogTitle>
      </DialogHeader>
      <CollegeForm onSubmit={onSubmit} initialData={initialData || undefined} />
    </DialogContent>
  </Dialog>
);

export default CollegeDialog;
