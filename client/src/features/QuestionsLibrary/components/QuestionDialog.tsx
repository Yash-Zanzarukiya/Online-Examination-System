"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { QuestionType } from "@/types/QuestionType";

interface EditDialogProps {
  questionId: string;
  questionType: QuestionType;
}

export function EditDialog({ questionId, questionType }: EditDialogProps) {
  const [open, setOpen] = useState(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="ghost">Edit</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Edit Question</DialogTitle>
          <DialogDescription>
            Hello! Question ID: {questionId}, Type: {QuestionType[questionType]}
          </DialogDescription>
        </DialogHeader>
        {/* Add your edit form here */}
      </DialogContent>
    </Dialog>
  );
}
