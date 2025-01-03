import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { useCategory } from "@/features/Question/hooks/useCategory";
import { UUID } from "crypto";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { useMemo } from "react";
import { Question } from "@/features/Question/types/question-types";

interface QuestionStatsProps {
  selectedQuestions: Question[] | [];
}

interface QuestionState {
  difficulty: Record<Difficulty, number>;
  type: Record<QuestionType, number>;
  category: Record<UUID, number>;
}

export default function QuestionStats({ selectedQuestions = [] }: QuestionStatsProps) {
  const { categories } = useCategory();

  const questionState: QuestionState = useMemo(() => {
    return {
      difficulty: {
        ...Object.values(Difficulty).reduce((acc, diff) => {
          acc[diff] = selectedQuestions.filter((question) => question.difficulty === diff).length;
          return acc;
        }, {} as Record<Difficulty, number>),
      },
      type: {
        ...Object.values(QuestionType).reduce((acc, type) => {
          acc[type] = selectedQuestions.filter((question) => question.type === type).length;
          return acc;
        }, {} as Record<QuestionType, number>),
      },
      category: {
        ...categories.reduce((acc, cat) => {
          acc[cat.name] = selectedQuestions.filter(
            (question) => question.category?.id === cat.id
          ).length;
          return acc;
        }, {} as Record<string, number>),
      },
    };
  }, [selectedQuestions, categories]);

  const totalQuestions = useMemo(() => selectedQuestions.length, [selectedQuestions]);

  return (
    <Card className="w-full max-w-7xl mx-auto">
      <CardContent className="p-3 px-7">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-6">
          <TypeSection type={questionState.type} />
          <DifficultySection difficulty={questionState.difficulty} />
          <CategorySection category={questionState.category} />
          <TotalQuestions total={totalQuestions} />
        </div>
      </CardContent>
    </Card>
  );
}

function DifficultySection({ difficulty }: { difficulty: QuestionState["difficulty"] }) {
  return (
    <div className="grid gap-2">
      <h4 className="text-md font-semibold text-foreground">Difficulty</h4>
      <div className="flex flex-wrap gap-2">
        {Object.entries(difficulty).map(([diff, count]) => (
          <Badge key={diff} variant="outline" className="border-red-600">
            {diff}: {count}
          </Badge>
        ))}
      </div>
    </div>
  );
}

function TypeSection({ type }: { type: QuestionState["type"] }) {
  return (
    <div className="grid gap-2">
      <h4 className="text-md font-semibold text-foreground">Type</h4>
      <div className="flex flex-wrap gap-2">
        {Object.entries(type).map(([t, count]) => (
          <Badge key={t} variant="outline" className="border-green-600">
            {t}: {count}
          </Badge>
        ))}
      </div>
    </div>
  );
}

function CategorySection({ category }: { category: QuestionState["category"] }) {
  return (
    <div className="grid gap-2">
      <h4 className="text-md font-semibold text-foreground">Category</h4>
      <div className="flex flex-wrap gap-2">
        {Object.entries(category).map(([cat, count]) => (
          <Badge key={cat} variant="outline" className="border-blue-600">
            {cat}: {count}
          </Badge>
        ))}
      </div>
    </div>
  );
}

function TotalQuestions({ total }: { total: number }) {
  return (
    <div className="flex flex-col items-center gap-2">
      <h4 className="text-md font-semibold text-foreground">Total</h4>
      <Badge className="text-md">{total}</Badge>
    </div>
  );
}
