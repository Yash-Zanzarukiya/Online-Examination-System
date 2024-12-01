import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useAppSelector } from "@/app/hooks";
import { ExamForm } from "../components";
import useExamForm from "../hooks/useExamForm";

export default function CreateExamPage() {
  const { isLoading } = useAppSelector(({ exams }) => exams);
  const { form, onSubmit } = useExamForm();

  return (
    <div className="container grow mx-auto p-10">
      <Button asChild className="mb-6">
        <Link to={"/admin/exams"}>← Back to Dashboard</Link>
      </Button>
      <ExamForm form={form} onSubmit={onSubmit} isLoading={isLoading} />
    </div>
  );
}
