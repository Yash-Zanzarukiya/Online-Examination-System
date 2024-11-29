import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { X } from "lucide-react";
import { FullQuestion } from "../../QuestionBuilder/types";

interface SelectedQuestionsListProps {
  questions: FullQuestion[];
  onRemove: (questionId: string) => void;
}

export default function SelectedQuestionsList({ questions, onRemove }: SelectedQuestionsListProps) {
  if (questions.length === 0) {
    return (
      <div className="text-center text-muted-foreground p-4">
        No questions selected. Select questions from the available questions list.
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {questions.map(({ question }, index) => (
        <Card key={question.id}>
          <CardContent className="p-4">
            <div className="flex justify-between items-start mb-2">
              <div className="space-y-1">
                <div className="flex items-center space-x-2">
                  <Badge>{index + 1}</Badge>
                  <Badge variant="outline">{question.type}</Badge>
                  <Badge variant="outline">{question.difficulty}</Badge>
                </div>
                <p className="text-sm text-muted-foreground">Category: {question.category.name}</p>
              </div>
              <Button
                variant="ghost"
                size="icon"
                onClick={() => onRemove(question.id)}
                className="text-destructive hover:text-destructive"
              >
                <X className="h-4 w-4" />
              </Button>
            </div>
            <p className="text-sm">{question.questionText}</p>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
