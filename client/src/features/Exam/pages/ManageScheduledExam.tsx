import { useParams } from "react-router-dom";
import { UUID } from "crypto";
import { ManageExamHeader } from "../components";
import useScheduledExam from "../hooks/useScheduledExam";

const tabs = [
  { name: "Candidates", to: "" },
  { name: "Questions", to: "pick" },
  { name: "Insights", to: "insights" },
  { name: "Schedule", to: "Schedule" },
];

export default function ManageScheduledExam() {
  const { scheduledExamId } = useParams();

  const { scheduledExam, loading } = useScheduledExam(scheduledExamId as UUID);

  if (loading) return <div>Loading...</div>;

  return <ManageExamHeader title={scheduledExam?.name} examTabs={tabs} />;
}
