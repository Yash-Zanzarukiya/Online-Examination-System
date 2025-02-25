import { useState } from "react";
import ConfirmDialog from "@/components/custom/ConfirmDialog";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { MoveRight, Pencil, Trash2 } from "lucide-react";
import { Exam } from "../types";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import { formatter } from "@/utils";
import { UUID } from "crypto";
import { useAppDispatch } from "@/app/hooks";
import { deleteExam } from "../redux/examThunks";
import { EditExamDialog } from "../components";

interface ExamCardProps {
  exam: Exam;
}

function ExamCard({ exam }: ExamCardProps) {
  const dispatch = useAppDispatch();
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const handleDeleteExam = (examId: UUID) => {
    dispatch(deleteExam(examId));
  };

  return (
    <>
      <Card key={exam.id} className="relative">
        <CardHeader>
          <CardTitle>
            <Link to={`manage/${exam.id}`}>{exam.title}</Link>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <p className="text-sm text-muted-foreground">
            Created: {formatter.formatTimestamp(exam.createdAt)}
          </p>
        </CardContent>
        <CardFooter className="flex justify-between items-center">
          <Button variant="outline" size="sm" asChild>
            <Link to={`manage/${exam.id}`}>Manage Questions</Link>
          </Button>
          <div className="space-x-2">
            <Button size="icon" variant="outline" onClick={() => setIsDialogOpen(true)}>
              <Pencil className="h-4 w-4" />
            </Button>
            <ConfirmDialog onConfirm={() => handleDeleteExam(exam.id)}>
              <Button size="icon" variant="outline">
                <Trash2 className="size-4 text-destructive" />
              </Button>
            </ConfirmDialog>
            <Button size="icon" variant="outline" asChild>
              <Link to={`manage/${exam.id}`}>
                <MoveRight />
              </Link>
            </Button>
          </div>
        </CardFooter>
      </Card>
      <EditExamDialog
        initialData={exam}
        isOpen={isDialogOpen}
        onClose={() => setIsDialogOpen(false)}
      />
    </>
  );
}

export default ExamCard;
