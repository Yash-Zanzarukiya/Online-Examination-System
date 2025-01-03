import { useAllExams } from "../hooks";
import { ExamCard } from ".";

function DraftedExams() {
  const { exams, isLoading } = useAllExams(true);

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-3">
      {!exams.length && !isLoading && <h1>No Drafted Exams</h1>}
      {exams.map((exam) => (
        <ExamCard key={exam.id} exam={exam} />
      ))}
    </div>
  );
}

export default DraftedExams;
