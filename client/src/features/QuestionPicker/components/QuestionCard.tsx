import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { CheckCircle2, Plus } from "lucide-react";
import { FullQuestion } from "../../QuestionBuilder/types";

interface QuestionCardProps {
  question: FullQuestion;
  onSelect: () => void;
  isSelected: boolean;
}

export default function QuestionCard({ question, onSelect, isSelected }: QuestionCardProps) {
  return (
    <Card className={`transition-colors ${isSelected ? "border-primary" : ""}`}>
      <CardContent className="p-4">
        <div className="flex justify-between items-start mb-2">
          <div className="space-y-1">
            <div className="flex items-center space-x-2">
              <Badge variant="outline">{question.question.type}</Badge>
              <Badge variant="outline">{question.question.difficulty}</Badge>
            </div>
            <p className="text-sm text-muted-foreground">
              Category: {question.question.category.name}
            </p>
          </div>
          <Button
            variant={isSelected ? "secondary" : "outline"}
            size="icon"
            onClick={onSelect}
            disabled={isSelected}
          >
            {isSelected ? (
              <CheckCircle2 className="h-4 w-4 text-primary" />
            ) : (
              <Plus className="h-4 w-4" />
            )}
          </Button>
        </div>
        <p className="text-sm line-clamp-2">{question.question.questionText}</p>
        {question.question.image && (
          <img
            src={question.question.image}
            alt="Question"
            className="mt-2 rounded-md w-full h-32 object-cover"
          />
        )}
        {question.options && (
          <div className="mt-2 space-y-1">
            {question.options.map((option, _) => (
              <div
                key={option.id}
                className={`text-sm p-2 rounded-md ${
                  option.correct ? "bg-primary/10" : "bg-muted"
                }`}
              >
                {option.optionText}
              </div>
            ))}
          </div>
        )}
      </CardContent>
    </Card>
  );
}
