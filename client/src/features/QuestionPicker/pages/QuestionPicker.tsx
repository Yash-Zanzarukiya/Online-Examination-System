import { useCallback, useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Loader2 } from "lucide-react";
import { FilterBar, QuestionList, QuestionDialog, QuestionStats } from "../components";
import { QuestionFilter } from "@/features/QuestionBuilder/types";
import { useQuestionDialog, useQuestionPickerForm } from "../hooks";

export default function QuestionPicker() {
  const { dialogQuestion, isDialogOpen, handleQuestionClick, setIsDialogOpen } =
    useQuestionDialog();

  const {
    availableQuestions,
    selectedQuestions,
    isLoading,
    isSubmitting,
    loadQuestions,
    handleAddQuestion,
    handleRemoveQuestion,
    clearAllQuestions,
    handleSubmit,
  } = useQuestionPickerForm();

  const [filter, setFilter] = useState<QuestionFilter>({});

  const handleFilterChange = useCallback(
    (newFilter: Partial<QuestionFilter>) => {
      setFilter((prev) => ({ ...prev, ...newFilter }));
      loadQuestions({ ...filter, ...newFilter });
    },
    [filter, loadQuestions]
  );

  return (
    <div className="container mx-auto p-4">
      <div className="space-y-5">
        {/* Header */}
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold">Question Bank</h1>

          <Button onClick={() => loadQuestions(filter)} disabled={isLoading}>
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
        <QuestionStats selectedQuestions={selectedQuestions} />

        <div className="grid md:grid-cols-2 gap-6">
          {/* Available Questions */}
          <Card>
            <CardContent className="p-4">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-semibold">Available Questions</h2>
                <span className="text-sm text-muted-foreground">
                  {availableQuestions.length} questions
                </span>
              </div>
              <ScrollArea className="h-[600px] pr-4">
                <QuestionList
                  questions={availableQuestions}
                  selectedQuestions={selectedQuestions}
                  onSelect={handleAddQuestion}
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
                <div className="flex gap-2">
                  <Button
                    size="sm"
                    variant="outline"
                    onClick={clearAllQuestions}
                    disabled={selectedQuestions.length === 0}
                  >
                    Clear
                  </Button>
                  <Button
                    size="sm"
                    onClick={handleSubmit}
                    disabled={isSubmitting || selectedQuestions.length === 0}
                  >
                    {isSubmitting ? (
                      <>
                        <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                        Saving
                      </>
                    ) : (
                      "Save All"
                    )}
                  </Button>
                </div>
              </div>
              <ScrollArea className="h-[600px] pr-4">
                <QuestionList
                  questions={selectedQuestions}
                  selectedQuestions={selectedQuestions}
                  onSelect={handleRemoveQuestion}
                  onQuestionClick={handleQuestionClick}
                  showRemoveButton
                />
              </ScrollArea>
            </CardContent>
          </Card>
        </div>

        {/* Question Dialog */}
        <QuestionDialog
          question={dialogQuestion}
          isOpen={isDialogOpen}
          onClose={() => setIsDialogOpen(false)}
        />
      </div>
    </div>
  );
}
