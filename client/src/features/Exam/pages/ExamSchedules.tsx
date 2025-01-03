import { useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ExamScheduleForm } from "../components";
import { UUID } from "crypto";
import { useExamSchedules } from "../hooks";

export default function ExamSchedules() {
  const { examId } = useParams();

  const { examSchedules, isLoading } = useExamSchedules(examId as UUID);

  return (
    <div className="container grow mx-auto p-10 flex flex-col gap-4">
      <Card className="w-full max-w-xl mx-auto">
        <CardHeader className="pb-0">
          <CardTitle>Schedule Exam</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid w-full items-center gap-2 mt-6">
            {examId && <ExamScheduleForm examId={examId as UUID} />}
          </div>
        </CardContent>
      </Card>
      {isLoading && <p>Loading...</p>}
      {examSchedules.map((examSchedule) => (
        <Card className="w-full max-w-xl mx-auto">
          <CardHeader className="pb-0">
            <CardTitle>Update Exam Schedule</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid w-full items-center gap-2 mt-6">
              <ExamScheduleForm
                key={examSchedule.id}
                examId={examId as UUID}
                initialData={examSchedule}
              />
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
