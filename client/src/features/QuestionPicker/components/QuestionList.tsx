import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Plus, Trash2 } from "lucide-react";
import { Question } from "@/features/Question/types/question-types";

interface QuestionListProps {
  questions: Question[];
  onSelect: (question: Question) => void;
  onQuestionClick: (question: Question) => void;
  showRemoveButton?: boolean;
}

export default function QuestionList({
  questions,
  onSelect,
  onQuestionClick,
  showRemoveButton = false,
}: QuestionListProps) {
  return (
    <div className="space-y-3">
      {questions.map((question) => {
        return (
          <Card key={question.id} className={`hover:bg-accent transition-colors`}>
            <CardContent className="p-4">
              <div className="flex items-center gap-4">
                <div className="flex-1 cursor-pointer" onClick={() => onQuestionClick(question)}>
                  <div className="flex items-center gap-2 mb-2">
                    <Badge variant="outline" className="border-green-600">
                      {question.type}
                    </Badge>
                    <Badge
                      variant={
                        question.difficulty.toLowerCase() as "default" | "secondary" | "destructive"
                      }
                      className="border-red-600"
                    >
                      {question.difficulty}
                    </Badge>
                    {question.category && (
                      <Badge variant="outline" className="border-blue-600">
                        {question.category.name}
                      </Badge>
                    )}
                  </div>
                  <p className="line-clamp-2 text-sm">{question.questionText}</p>
                </div>
                <div className="flex items-center gap-2 shrink-0">
                  {showRemoveButton ? (
                    <Button variant="outline" size="icon" onClick={() => onSelect(question)}>
                      <Trash2 className="h-4 w-4 text-destructive" />
                    </Button>
                  ) : (
                    <Button variant={"outline"} size="icon" onClick={() => onSelect(question)}>
                      <Plus className="h-4 w-4" />
                    </Button>
                  )}
                </div>
              </div>
            </CardContent>
          </Card>
        );
      })}
      {questions.length === 0 && (
        <div className="text-center py-8 text-muted-foreground">No questions found</div>
      )}
    </div>
  );
}
