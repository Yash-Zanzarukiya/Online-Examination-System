import { useParams } from "react-router-dom";
import { Card, CardContent } from "@/components/ui/card";
import { ExamScheduleForm } from "../components";
import { UUID } from "crypto";
import useScheduledExam from "../hooks/useScheduledExam";

export default function UpdateExamSchedule() {
  const { scheduledExamId } = useParams();

  const { scheduledExam, loading } = useScheduledExam(scheduledExamId as UUID);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="container grow mx-auto p-10">
      <Card className="w-full max-w-xl mx-auto">
        <CardContent>
          <div className="grid w-full items-center gap-2 mt-6">
            {scheduledExam && (
              <ExamScheduleForm examId={scheduledExam.examId} initialData={scheduledExam} />
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
