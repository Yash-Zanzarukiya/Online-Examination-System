import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import { FullQuestion } from "../../QuestionBuilder/types";

interface QuestionDialogProps {
  question: FullQuestion | null;
  isOpen: boolean;
  onClose: () => void;
}

export default function QuestionDialog({ question, isOpen, onClose }: QuestionDialogProps) {
  if (!question) return null;

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>Question Details</DialogTitle>
        </DialogHeader>
        <div className="space-y-4">
          <div className="flex flex-wrap gap-2">
            <Badge variant="outline">{question.question.type}</Badge>
            <Badge variant="outline">{question.question.difficulty}</Badge>
            <Badge variant="outline">{question.question.category.name}</Badge>
          </div>

          <div className="space-y-4">
            <div>
              <h3 className="font-medium mb-2">Question</h3>
              <p className="text-sm">{question.question.questionText}</p>
              {question.question.image && (
                <img
                  src={question.question.image}
                  alt="Question"
                  className="mt-2 rounded-md w-full max-h-48 object-cover"
                />
              )}
            </div>

            {question.options && (
              <div>
                <h3 className="font-medium mb-2">Options</h3>
                <div className="grid gap-2">
                  {question.options.map((option) => (
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
