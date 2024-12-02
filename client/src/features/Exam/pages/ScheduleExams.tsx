import { Link } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { TypographyH3 } from "@/components/ui/TypographyH3";
import { Button } from "@/components/ui/button";
import { ExamScheduleForm } from "../components";
import { useExams } from "../hooks";
import { UUID } from "crypto";
import { useMemo, useState } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

export default function ScheduleExam() {
  const { exams } = useExams();
  const [selectedExamId, setSelectedExamId] = useState<UUID | null>(null);

  const exam = useMemo(() => exams.find((e) => e.id === selectedExamId), [selectedExamId]);

  return (
    <div className="container grow mx-auto p-10">
      <Button asChild className="mb-6">
        <Link to={"/admin/exams"}>← Back to Dashboard</Link>
      </Button>
      <Card className="w-full max-w-xl mx-auto">
        <CardHeader>
          <CardTitle>
            <TypographyH3>Schedule Exam</TypographyH3>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <Select onValueChange={(value: UUID) => setSelectedExamId(value)}>
            <SelectTrigger id="exam-select">
              <SelectValue placeholder="Select an exam" />
            </SelectTrigger>
            <SelectContent>
              {exams.map((exam) => (
                <SelectItem key={exam.id} value={exam.id}>
                  {exam.title}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
          <div className="grid w-full items-center gap-2 mt-6">
            {selectedExamId && (
              <ExamScheduleForm examId={selectedExamId} startDate={exam?.startDate} />
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
