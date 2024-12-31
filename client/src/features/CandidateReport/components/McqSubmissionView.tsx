import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useMcqSubmission } from "../hooks";
import { UUID } from "crypto";
import { Badge } from "@/components/ui/badge";
import { TypographyH4 } from "@/components/ui/TypographyH4";

function McqSubmissionView({ questionId }: { questionId: UUID }) {
  const { isLoading, mcqSubmission } = useMcqSubmission(questionId);

  if (isLoading || !mcqSubmission) {
    return <div>Loading...</div>;
  }

  const { question, selectedOptionId, score } = mcqSubmission;

  return (
    <Card>
      <CardHeader className="py-4">
        <CardTitle>
          <div className="flex justify-between">
            <div className="text-sm text-muted-foreground flex gap-2">
              <Badge variant="outline" className="border-green-600">
                {question.question.type}
              </Badge>
              <Badge variant="outline" className="border-red-600">
                {question.question.difficulty}
              </Badge>
              <Badge variant="outline" className="border-blue-600">
                {question.question.category.name}
              </Badge>
            </div>
            <div className="flex items-center justify-center gap-1">
              Score : <TypographyH4>{score}</TypographyH4>/ {question.question.marks}
            </div>
          </div>
        </CardTitle>
      </CardHeader>

      <CardContent className="space-y-4 pt-0">
        <div className="space-y-2">
          <h4 className="font-semibold">Question</h4>
          <div className="whitespace-pre-wrap rounded-lg bg-muted p-4">
            {question.question.questionText}
          </div>
        </div>

        <div className="space-y-4">
          <h4 className="font-semibold">Options</h4>
          <RadioGroup value={selectedOptionId} disabled>
            {question.options.map((option: any) => (
              <div
                key={option.id}
                className={`flex items-center space-x-2 p-4 rounded-lg border ${
                  option.correct
                    ? "bg-green-50 border-green-600"
                    : selectedOptionId === option.id
                    ? "bg-red-50 border-destructive"
                    : ""
                }`}
              >
                <RadioGroupItem value={option.id} id={option.id} />
                <Label
                  htmlFor={option.id}
                  className={`flex-1 ${
                    option.correct
                      ? "text-green-700 border-green-600"
                      : selectedOptionId === option.id
                      ? "text-destructive"
                      : ""
                  }`}
                >
                  {option.optionText}
                </Label>
                {option.correct && <span className="text-green-600 text-sm">Correct Answer</span>}
                {selectedOptionId === option.id && !option.correct && (
                  <span className="text-red-600 text-sm">Selected Answer</span>
                )}
              </div>
            ))}
          </RadioGroup>
        </div>
      </CardContent>
    </Card>
  );
}

export default McqSubmissionView;
