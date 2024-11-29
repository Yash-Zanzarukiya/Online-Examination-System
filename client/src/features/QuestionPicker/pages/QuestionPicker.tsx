import { useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Loader2 } from "lucide-react";
import { FilterBar, QuestionList, QuestionDialog, QuestionStats } from "../components";
import { useAppDispatch } from "@/app/hooks";
import { UUID } from "crypto";
import { getAllFullQuestionsThunk } from "@/features/QuestionBuilder/redux/questionThunks";
import { FullQuestion, QuestionFilter } from "@/features/QuestionBuilder/types";

export default function QuestionPicker() {
  const dispatch = useAppDispatch();
  const [filter, setFilter] = useState<QuestionFilter>({});
  const [questions, setQuestions] = useState<FullQuestion[]>([]);
  const [selectedQuestions, setSelectedQuestions] = useState<FullQuestion[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedQuestion, setSelectedQuestion] = useState<FullQuestion | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const loadQuestions = async () => {
    setIsLoading(true);
    try {
      const response = await dispatch(getAllFullQuestionsThunk(filter)).unwrap();
      if (response) setQuestions(response);
    } catch (error) {
      console.error("Error loading questions:", error);
    }
    setIsLoading(false);
  };

  const handleFilterChange = (newFilter: Partial<QuestionFilter>) => {
    setFilter({ ...filter, ...newFilter });
    loadQuestions();
  };

  const handleSelectQuestion = (question: FullQuestion) => {
    if (!selectedQuestions.find((q) => q.question.id === question.question.id)) {
      setSelectedQuestions([...selectedQuestions, question]);
    }
  };

  const handleRemoveQuestion = (questionId: UUID) => {
    setSelectedQuestions(selectedQuestions.filter((q) => q.question.id !== questionId));
  };

  const handleQuestionClick = (question: FullQuestion) => {
    setSelectedQuestion(question);
    setIsDialogOpen(true);
  };

  return (
    <div className="container mx-auto p-6">
      <div className="space-y-6">
        {/* Header */}
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold">Question Bank</h1>
          <Button onClick={loadQuestions} disabled={isLoading}>
            {isLoading ? (
              <>
                <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                Loading...
              </>
            ) : (
              "Refresh Questions"
            )}
          </Button>
        </div>

        {/* Filters */}
        <Card className="border-dashed">
          <CardContent className="p-4">
            <FilterBar onFilterChange={handleFilterChange} currentFilter={filter} />
          </CardContent>
        </Card>

        {/* Stats */}
        <QuestionStats questions={selectedQuestions} />

        {/* Questions Grid */}
        <div className="grid md:grid-cols-2 gap-6">
          {/* Available Questions */}
          <Card>
            <CardContent className="p-4">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-semibold">Available Questions</h2>
                <span className="text-sm text-muted-foreground">{questions.length} questions</span>
              </div>
              <ScrollArea className="h-[600px] pr-4">
                <QuestionList
                  questions={questions}
                  selectedQuestions={selectedQuestions}
                  onSelect={handleSelectQuestion}
                  onQuestionClick={handleQuestionClick}
                />
              </ScrollArea>
            </CardContent>
          </Card>

          {/* Selected Questions */}
          <Card>
            <CardContent className="p-4">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-semibold">Selected Questions</h2>
                <span className="text-sm text-muted-foreground">
                  {selectedQuestions.length} selected
                </span>
              </div>
              <ScrollArea className="h-[600px] pr-4">
                <QuestionList
                  questions={selectedQuestions}
                  selectedQuestions={selectedQuestions}
                  onSelect={(question) => handleRemoveQuestion(question.question.id)}
                  onQuestionClick={handleQuestionClick}
                  showRemoveButton
                />
              </ScrollArea>
            </CardContent>
          </Card>
        </div>

        {/* Question Dialog */}
        <QuestionDialog
          question={selectedQuestion}
          isOpen={isDialogOpen}
          onClose={() => setIsDialogOpen(false)}
        />
      </div>
    </div>
  );
}
