import { Button } from "@/components/ui/button";
import { TypographyH2 } from "@/components/ui/TypographyH2";
import { Link } from "react-router-dom";
import { useExams } from "../hooks";
import { ExamCard } from "../components";

function ManageExams() {
  const { exams } = useExams();

  return (
    <div className="container grow mx-auto p-10">
      <Button asChild className="mb-4">
        <Link to={"/admin/exams"}>← Back to Dashboard</Link>
      </Button>
      <TypographyH2>Manage Exams</TypographyH2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-2">
        {exams.map((exam) => (
          <ExamCard key={exam.id} exam={exam} />
        ))}
      </div>
    </div>
  );
}

export default ManageExams;
