import { Card, CardContent } from "@/components/ui/card";
import { Form } from "@/components/ui/form";
import { Button } from "@/components/ui/button";
import { Loader2 } from "lucide-react";
import { QuestionTypeSelector, QuestionEditor, OptionsList } from ".";
import { useQuestionForm } from "../hooks/useQuestionForm";
import { FullQuestion, QuestionCreatePayload } from "../types";
import { QuestionType } from "@/types/QuestionType";

// function QuestionComponent() {
//   const { form, isSubmitting, handleSubmit, handleAddOption, handleDeleteOption } =
//     useQuestionForm();

//   return (
//     <Form {...form}>
//       <form onSubmit={form.handleSubmit(handleSubmit)}>
//         <Card className="mb-6 relative">
//           <CardContent className="relative p-4">
//             <QuestionTypeSelector control={form.control} />
//             <Button type="submit" disabled={isSubmitting} className="absolute right-4 top-12">
//               {isSubmitting ? (
//                 <>
//                   <Loader2 className="mr-2 h-4 w-4 animate-spin" />
//                   Saving...
//                 </>
//               ) : (
//                 "Save Full Question"
//               )}
//             </Button>
//             <QuestionEditor form={form} />
//             <OptionsList
//               control={form.control}
//               onAddOption={handleAddOption}
//               onDeleteOption={handleDeleteOption}
//             />
//           </CardContent>
//         </Card>
//       </form>
//     </Form>
//   );
// }

interface QuestionComponentProps {
  initialData?: FullQuestion | QuestionCreatePayload;
  onUpdate?: (data: QuestionCreatePayload) => void;
}

function QuestionComponent({ initialData, onUpdate }: QuestionComponentProps) {
  const { form, isSubmitting, handleSubmit, handleAddOption, handleDeleteOption } = useQuestionForm(
    initialData as FullQuestion
  );

  const handleFormSubmit = (data: QuestionCreatePayload) => {
    handleSubmit(data);
    if (onUpdate) onUpdate(data);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleFormSubmit)}>
        <Card className="mb-6 relative bg-muted/20">
          <CardContent className="relative p-4">
            <QuestionTypeSelector control={form.control} />
            <Button type="submit" disabled={isSubmitting} className="absolute right-4 top-12">
              {isSubmitting ? (
                <>
                  <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  Saving...
                </>
              ) : (
                "Save Full Question"
              )}
            </Button>
            <QuestionEditor form={form} />
            {form.getValues("question.type") === QuestionType.MCQ && (
              <OptionsList
                control={form.control}
                onAddOption={handleAddOption}
                onDeleteOption={handleDeleteOption}
              />
            )}
          </CardContent>
        </Card>
      </form>
    </Form>
  );
}

export default QuestionComponent;
