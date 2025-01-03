import { useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Form } from "@/components/ui/form";
import { useForm } from "react-hook-form";

interface ExamInviteDialogProps {
  isOpen: boolean;
  onClose: () => void;
  onImport: (file: File) => void;
}

function ExamInviteDialog({ isOpen, onClose, onImport }: ExamInviteDialogProps) {
  const [file, setFile] = useState<File | null>(null);
  const form = useForm();

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = event.target.files?.[0];
    if (selectedFile) {
      setFile(selectedFile);
    }
  };

  const handleSubmit = form.handleSubmit(() => {
    if (file) {
      onImport(file);
      onClose();
    }
  });

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-lg">
        <DialogHeader>
          <DialogTitle>
            <div className="flex gap-1">
              <span>Import from excel file</span>
            </div>
          </DialogTitle>
        </DialogHeader>

        <span>
          <b>Note: </b>The first cell should contains emails
        </span>

        <Form {...form}>
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
            <Button type="submit" disabled={!file}>
              Send Invitation Email
            </Button>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}

export default ExamInviteDialog;
