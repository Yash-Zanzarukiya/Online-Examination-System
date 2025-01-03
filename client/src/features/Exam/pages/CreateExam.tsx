import { useAppSelector } from "@/app/hooks";
import { ExamForm } from "../components";
import { useExamForm } from "../hooks";

export default function CreateExamPage() {
  const { isLoading } = useAppSelector(({ exams }) => exams);
  const { form, onSubmit } = useExamForm();

  return (
    <div className="container grow mx-auto p-10">
      <ExamForm form={form} onSubmit={onSubmit} isLoading={isLoading} />
    </div>
  );
}
