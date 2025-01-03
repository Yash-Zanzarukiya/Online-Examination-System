import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import { useFullQuestion } from "@/features/Question/hooks";
import { Question } from "@/features/Question/types/question-types";

interface QuestionDialogProps {
  question: Question | null;
  isOpen: boolean;
  onClose: () => void;
}

export default function QuestionDialog({ question, isOpen, onClose }: QuestionDialogProps) {
  if (!question) return null;

  const { fullQuestion, loading } = useFullQuestion(question.id, question.type);

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>Question Details</DialogTitle>
        </DialogHeader>
        <div className="space-y-4">
          <div className="flex flex-wrap gap-2">
            <Badge variant="outline" className=" border-green-600">
              {question.type}
            </Badge>
            <Badge variant="outline" className=" border-red-600">
              {question.difficulty}
            </Badge>
            {question.category && (
              <Badge variant="outline" className=" border-blue-600">
                {question.category.name}
              </Badge>
            )}
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
            {fullQuestion?.referenceAnswer && (
              <div>
                <h3 className="font-medium mb-2">Reference Answer</h3>
                <div className={`p-3 rounded-md text-sm bg-muted`}>
                  {fullQuestion.referenceAnswer}
                </div>
              </div>
            )}
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
