import { useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Form } from "@/components/ui/form";
import { useForm } from "react-hook-form";
import QuestionUploadFormat from "./QuestionUploadFormat";

interface ImportQuestionDataDialogProps {
  isOpen: boolean;
  onClose: () => void;
  onImport: (file: File) => void;
}

function ImportQuestionDataDialog({ isOpen, onClose, onImport }: ImportQuestionDataDialogProps) {
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
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>
            <div className="flex gap-1">
              <span>Import Student Data</span>
            </div>
          </DialogTitle>
        </DialogHeader>
        <span className="font-semibold tracking-tight text-md">Sample format</span>

        <Form {...form}>
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
            <Button type="submit" disabled={!file}>
              Import
            </Button>
          </form>
        </Form>
        <QuestionUploadFormat />
      </DialogContent>
    </Dialog>
  );
}

export default ImportQuestionDataDialog;
