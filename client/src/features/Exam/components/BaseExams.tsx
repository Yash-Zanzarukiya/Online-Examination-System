import { ExamCard } from ".";
import { useAllExams } from "../hooks";

function BaseExams() {
  const { exams, isLoading } = useAllExams();

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-3">
      {!exams.length && !isLoading && <h1>No Exams Found</h1>}
      {exams.map((exam) => (
        <ExamCard key={exam.id} exam={exam} />
      ))}
    </div>
  );
}

export default BaseExams;
