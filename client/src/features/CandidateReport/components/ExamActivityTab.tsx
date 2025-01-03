import { useExamActivityTab } from "../hooks";
import ExamActivityTable from "./ExamActivityTable";

function ExamActivityTab() {
  const { examActivities, isLoading } = useExamActivityTab();

  if (isLoading) return <div>Loading...</div>;

  return <ExamActivityTable activities={examActivities} />;
}

export default ExamActivityTab;
