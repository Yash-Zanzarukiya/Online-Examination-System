import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Form } from "@/components/ui/form";
import { FormInput } from "@/components/custom/FormInput";
import { Loader2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { TypographyH2 } from "@/components/ui/TypographyH2";

interface ExamFormProps {
  form: any;
  onSubmit: any;
  isLoading: boolean;
  isUpdating?: boolean;
}

function ExamForm({ form, onSubmit, isLoading, isUpdating = false }: ExamFormProps) {
  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        <Card className="w-full max-w-xl mx-auto">
          <CardHeader>
            <CardTitle>
              {isUpdating ? (
                <TypographyH2>Update Exam</TypographyH2>
              ) : (
                <TypographyH2>Create Exam</TypographyH2>
              )}
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid w-full items-center gap-2">
              <FormInput
                control={form.control}
                name="title"
                label="Exam Name"
                placeholder="Enter exam name"
              />
              <FormInput
                control={form.control}
                name="passingScore"
                label="Passing Score"
                placeholder="Enter passing score"
                type="number"
              />
              <FormInput
                control={form.control}
                name="timeLimit"
                label="Duration (in minutes)"
                placeholder="Enter exam duration"
                type="number"
              />
            </div>
          </CardContent>
          <CardFooter className="flex justify-end">
            <Button type="submit" disabled={isLoading}>
              {isLoading && (
                <>
                  <Loader2 className="size-4 animate-spin" />
                  <span>{isUpdating ? "Updating" : "Creating"} Exam...</span>
                </>
              )}
              {!isLoading && (isUpdating ? "Update Exam" : "Create Exam")}
            </Button>
          </CardFooter>
        </Card>
      </form>
    </Form>
  );
}

export default ExamForm;
