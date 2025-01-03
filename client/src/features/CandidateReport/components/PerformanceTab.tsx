import QuestionsTable from "./QuestionsTable";
import ScoreDistributionSection from "./ScoreDistributionSection";
import { usePerformanceTab } from "../hooks";

export default function PerformanceTab() {
  const { questionsAnalysis, scoreDistribution } = usePerformanceTab();

  return (
    <div className="space-y-6 p-6 pt-2">
      <ScoreDistributionSection scoreDistribution={scoreDistribution} />
      <QuestionsTable questionsAnalysis={questionsAnalysis} />
    </div>
  );
}
