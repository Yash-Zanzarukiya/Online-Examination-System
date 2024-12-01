import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import { FullQuestion, Question } from "@/features/QuestionBuilder/types";
import questionApi from "@/features/QuestionBuilder/api/questionApi";
import { useEffect, useState } from "react";

interface QuestionDialogProps {
  question: Question | null;
  isOpen: boolean;
  onClose: () => void;
}

export default function QuestionDialog({ question, isOpen, onClose }: QuestionDialogProps) {
  if (!question) return null;

  const [fullQuestion, setFullQuestion] = useState<FullQuestion | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchFullQuestion = async () => {
      try {
        setLoading(true);
        const response = await questionApi.getFullQuestionByIdApi(question.id);
        setFullQuestion(response.data.data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    if (question?.id) fetchFullQuestion();
  }, [question]);

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>Question Details</DialogTitle>
        </DialogHeader>
        <div className="space-y-4">
          <div className="flex flex-wrap gap-2">
            <Badge variant="outline">{question.difficulty}</Badge>
            <Badge variant="outline">{question.type}</Badge>
            <Badge variant="outline">{question.category?.name}</Badge>
          </div>

          <div className="space-y-4">
            <div>
              <h3 className="font-medium mb-2">Question</h3>
              <p className="text-sm">{question.questionText}</p>
              {question.image && (
                <img
                  src={question.image}
                  alt="Question"
                  className="mt-2 rounded-md w-full max-h-48 object-cover"
                />
              )}
            </div>

            {loading && <div>Loading...</div>}

            {fullQuestion?.options && (
              <div>
                <h3 className="font-medium mb-2">Options</h3>
                <div className="grid gap-2">
                  {fullQuestion.options.map((option) => (
                    <div
                      key={option.id}
                      className={`p-3 rounded-md text-sm ${
                        option.correct ? "bg-primary/10 border border-primary/20" : "bg-muted"
                      }`}
                    >
                      <div className="flex items-start gap-2">
                        <span>{option.optionText}</span>
                        {option.correct && (
                          <Badge variant="secondary" className="ml-auto">
                            Correct
                          </Badge>
                        )}
                      </div>
                      {option.image && (
                        <img
                          src={option.image}
                          alt="Option"
                          className="mt-2 rounded-md max-h-32 object-cover"
                        />
                      )}
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
