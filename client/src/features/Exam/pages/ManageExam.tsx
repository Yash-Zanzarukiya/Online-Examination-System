import { useParams } from "react-router-dom";
import { UUID } from "crypto";
import { ManageExamHeader } from "../components";
import { useExam } from "../hooks";

const tabs = [
  { name: "Questions", to: "" },
  { name: "Schedule", to: "Schedule" },
];

export default function ManageExam() {
  const { examId } = useParams();

  const { exam, loading } = useExam(examId as UUID);

  if (loading) return <div>Loading...</div>;

  return <ManageExamHeader title={exam?.title} examTabs={tabs} normal />;
}
