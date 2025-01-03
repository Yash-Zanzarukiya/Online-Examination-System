import { Card, CardContent } from "@/components/ui/card";
import { Form } from "@/components/ui/form";
import { Button } from "@/components/ui/button";
import { QuestionTypeSelector, QuestionEditor, OptionsList } from ".";
import { McqQuestion } from "@/features/Question/types/mcq-types";
import { useMcqQuestionForm } from "../hooks/useMcqQuestionForm";

interface McqQuestionFormProps {
  initialData?: McqQuestion;
}

function McqQuestionFormComponent({ initialData }: McqQuestionFormProps) {
  const {
    form,
    handleSubmit,
    handleAddOption,
    handleDeleteOption,
    handleDeleteQuestion,
    handleUpdateQuestion,
    handleUpdateOption,
  } = useMcqQuestionForm(initialData);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSubmit)}>
        <Card className="mb-6 relative bg-muted/20">
          <CardContent className="relative p-4">
            {/* Selection of type, category and difficulty */}
            <QuestionTypeSelector control={form.control} />

            <Button type="submit" className="absolute right-4 top-4">
              Save
            </Button>

            {/* Question Editor */}
            <QuestionEditor
              form={form}
              onDeleteQuestion={handleDeleteQuestion}
              onUpdateQuestion={handleUpdateQuestion}
            />

            {/* Mcq Option List */}
            <OptionsList
              control={form.control}
              onAddOption={handleAddOption}
              onDeleteOption={handleDeleteOption}
              onUpdateOption={handleUpdateOption}
            />
          </CardContent>
        </Card>
      </form>
    </Form>
  );
}

export default McqQuestionFormComponent;
