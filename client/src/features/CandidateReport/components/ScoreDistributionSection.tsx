import { Card, CardContent } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { ScoreDistribution } from "../types";

interface ScoreDistributionSectionProps {
  scoreDistribution: ScoreDistribution | null;
}

function ScoreDistributionSection({ scoreDistribution }: ScoreDistributionSectionProps) {
  if (!scoreDistribution) return <div>Loading...</div>;

  const { aggregatedMarks, aggregatedScores } = scoreDistribution;

  const scoreItems = [
    { name: "Total Score", score: aggregatedScores.totalScore, total: aggregatedMarks.totalMarks },
    {
      name: "MCQ Questions",
      score: aggregatedScores.mcqQuestionScore,
      total: aggregatedMarks.mcqQuestionTotalMarks,
    },
    {
      name: "Programming Questions",
      score: aggregatedScores.programmingQuestionScore,
      total: aggregatedMarks.programmingQuestionTotalMarks,
    },
    {
      name: "Easy Questions",
      score: aggregatedScores.easyQuestionScore,
      total: aggregatedMarks.easyQuestionTotalMarks,
    },
    {
      name: "Medium Questions",
      score: aggregatedScores.mediumQuestionScore,
      total: aggregatedMarks.mediumQuestionTotalMarks,
    },
    {
      name: "Hard Questions",
      score: aggregatedScores.hardQuestionScore,
      total: aggregatedMarks.hardQuestionTotalMarks,
    },
    {
      name: "Aptitude MCQ",
      score: aggregatedScores.aptitudeMcqScore,
      total: aggregatedMarks.aptitudeMcqTotalMarks,
    },
    {
      name: "Technical MCQ",
      score: aggregatedScores.technicalMcqScore,
      total: aggregatedMarks.technicalMcqTotalMarks,
    },
    {
      name: "Programming MCQ",
      score: aggregatedScores.programmingMcqScore,
      total: aggregatedMarks.programmingMcqTotalMarks,
    },
  ];

  return (
    <Card>
      <CardContent className="pb-4">
        <Accordion type="single" collapsible className="w-full">
          <AccordionItem value="score-distribution" className="border-none">
            <AccordionTrigger className="pb-0">
              <h1 className="text-base">
                Score Distribution • {aggregatedScores.totalScore}/{aggregatedMarks.totalMarks}
              </h1>
            </AccordionTrigger>
            <AccordionContent>
              <div className="space-y-4">
                {scoreItems.map((item, index) => (
                  <div key={index}>
                    <div className="flex justify-between mb-1">
                      <span className="text-sm font-medium">{item.name}</span>
                      <span className="text-sm font-medium">
                        {item.score} / {item.total}
                      </span>
                    </div>
                    <Progress value={(item.score / item.total) * 100} className="h-2" />
                  </div>
                ))}
              </div>
            </AccordionContent>
          </AccordionItem>
        </Accordion>
      </CardContent>
    </Card>
  );
}

export default ScoreDistributionSection;
