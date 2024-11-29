import { Card, CardContent } from "@/components/ui/card";
import { FullQuestion } from "../../QuestionBuilder/types";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";

interface QuestionStats {
  total: number;
  byDifficulty: Record<Difficulty, number>;
  byType: Record<QuestionType, number>;
  byCategory: Record<string, number>;
}

interface QuestionStatsProps {
  questions: FullQuestion[];
}

export default function QuestionStats({ questions }: QuestionStatsProps) {
  const stats: QuestionStats = {
    total: questions.length,
    byDifficulty: {
      [Difficulty.EASY]: 0,
      [Difficulty.MEDIUM]: 0,
      [Difficulty.HARD]: 0,
    },
    byType: {
      [QuestionType.MCQ]: 0,
      [QuestionType.PROGRAMMING]: 0,
    },
    byCategory: {},
  };

  questions.forEach((question) => {
    stats.byDifficulty[question.question.difficulty]++;
    stats.byType[question.question.type]++;
    const categoryName = question.question.category.name;
    stats.byCategory[categoryName] = (stats.byCategory[categoryName] || 0) + 1;
  });

  return (
    <Card>
      <CardContent className="p-4">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div>
            <h3 className="text-sm font-medium text-muted-foreground">Total Selected</h3>
            <p className="text-2xl font-bold">{stats.total}</p>
          </div>
          <div>
            <h3 className="text-sm font-medium text-muted-foreground">By Difficulty</h3>
            <div className="space-y-1">
              {Object.entries(stats.byDifficulty).map(
                ([difficulty, count]) =>
                  count > 0 && (
                    <div key={difficulty} className="flex justify-between text-sm">
                      <span>{difficulty}</span>
                      <span>{count}</span>
                    </div>
                  )
              )}
            </div>
          </div>
          <div>
            <h3 className="text-sm font-medium text-muted-foreground">By Type</h3>
            <div className="space-y-1">
              {Object.entries(stats.byType).map(
                ([type, count]) =>
                  count > 0 && (
                    <div key={type} className="flex justify-between text-sm">
                      <span>{type}</span>
                      <span>{count}</span>
                    </div>
                  )
              )}
            </div>
          </div>
          <div>
            <h3 className="text-sm font-medium text-muted-foreground">By Category</h3>
            <div className="space-y-1">
              {Object.entries(stats.byCategory).map(([category, count]) => (
                <div key={category} className="flex justify-between text-sm">
                  <span>{category}</span>
                  <span>{count}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
