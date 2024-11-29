import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Plus, Check, Trash2 } from "lucide-react";
import { FullQuestion } from "../../QuestionBuilder/types";

interface QuestionListProps {
  questions: FullQuestion[];
  selectedQuestions: FullQuestion[];
  onSelect: (question: FullQuestion) => void;
  onQuestionClick: (question: FullQuestion) => void;
  showRemoveButton?: boolean;
}

export default function QuestionList({
  questions,
  selectedQuestions,
  onSelect,
  onQuestionClick,
  showRemoveButton = false,
}: QuestionListProps) {
  return (
    <div className="space-y-3">
      {questions.map((fullQuestion) => {
        const isSelected = selectedQuestions.some(
          (q) => q.question.id === fullQuestion.question.id
        );
        const { question } = fullQuestion;

        return (
          <Card
            key={question.id}
            className={`${
              isSelected ? "border-primary bg-primary/5" : "hover:bg-accent"
            } transition-colors`}
          >
            <CardContent className="p-4">
              <div className="flex items-center gap-4">
                <div
                  className="flex-1 cursor-pointer"
                  onClick={() => onQuestionClick(fullQuestion)}
                >
                  <div className="flex items-center gap-2 mb-2">
                    <Badge variant="outline" className="bg-background">
                      {question.category.name}
                    </Badge>
                    <Badge
                      variant={
                        question.difficulty.toLowerCase() as "default" | "secondary" | "destructive"
                      }
                    >
                      {question.difficulty}
                    </Badge>
                    <Badge variant="outline" className="bg-background">
                      {question.type}
                    </Badge>
                  </div>
                  <p className="line-clamp-2 text-sm">{question.questionText}</p>
                </div>
                <div className="flex items-center gap-2 shrink-0">
                  {showRemoveButton ? (
                    <Button
                      variant="destructive"
                      size="icon"
                      onClick={() => onSelect(fullQuestion)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  ) : (
                    <Button
                      variant={isSelected ? "secondary" : "outline"}
                      size="icon"
                      onClick={() => onSelect(fullQuestion)}
                      disabled={isSelected}
                    >
                      {isSelected ? <Check className="h-4 w-4" /> : <Plus className="h-4 w-4" />}
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
