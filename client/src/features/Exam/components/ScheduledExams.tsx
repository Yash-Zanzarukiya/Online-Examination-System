import { ScheduledExamCard } from ".";
import { useScheduledExams } from "../hooks";

function ScheduledExams() {
  const { scheduledExams, isLoading } = useScheduledExams();

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-3">
      {!scheduledExams.length && !isLoading && <h1>No Exams are Scheduled</h1>}
      {scheduledExams.map((scheduledExam) => (
        <ScheduledExamCard key={scheduledExam.id} scheduledExam={scheduledExam} />
      ))}
    </div>
  );
}

export default ScheduledExams;
