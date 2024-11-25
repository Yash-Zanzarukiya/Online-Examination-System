import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import { Form } from "@/components/ui/form";
import { collegeSchema, CollegeFormData } from "../validators";
import { CollegeDTO } from "../types";
import { FormInput } from "@/components/custom/FormInput";

interface CollegeFormProps {
  onSubmit: (data: CollegeFormData) => void;
  initialData?: CollegeDTO;
}

export function CollegeForm({ onSubmit, initialData }: CollegeFormProps) {
  const form = useForm<CollegeFormData>({
    resolver: zodResolver(collegeSchema),
    defaultValues: {
      name: initialData?.name || "",
    },
  });

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        <FormInput
          control={form.control}
          name="name"
          label="College Name"
          placeholder="Enter college name"
        />

        <Button type="submit" className="w-full">
          {initialData ? "Update College" : "Add College"}
        </Button>
      </form>
    </Form>
  );
}
