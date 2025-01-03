import { useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Form } from "@/components/ui/form";
import { useForm } from "react-hook-form";
import CollegeSelector from "./CollegeSelector";
import { UUID } from "crypto";
import { HoverCard, HoverCardContent, HoverCardTrigger } from "@/components/ui/hover-card";

interface ImportStudentDataDialogProps {
  isOpen: boolean;
  onClose: () => void;
  onImport: (collegeId: UUID, file: File) => void;
}

export function ImportStudentDataDialog({
  isOpen,
  onClose,
  onImport,
}: ImportStudentDataDialogProps) {
  const [file, setFile] = useState<File | null>(null);
  const form = useForm();

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = event.target.files?.[0];
    if (selectedFile) {
      setFile(selectedFile);
    }
  };

  const handleSubmit = form.handleSubmit((data) => {
    if (file && data.collegeId) {
      onImport(data.collegeId, file);
      onClose();
    }
  });

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            <div className="flex gap-1">
              <span>Import Student Data - </span>
              <HoverCard>
                <HoverCardTrigger>
                  <span className="font-bold tracking-tight text-md">Sample format</span>
                </HoverCardTrigger>
                <HoverCardContent className="w-full p-2 text-sm">
                  email - fullName - branch - phone - passout
                </HoverCardContent>
              </HoverCard>
            </div>
          </DialogTitle>
        </DialogHeader>

        <Form {...form}>
          <form onSubmit={handleSubmit} className="space-y-4">
            <CollegeSelector control={form.control} />
            <div>
              <Input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
            </div>
            <Button type="submit" disabled={!file}>
              Import
            </Button>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
