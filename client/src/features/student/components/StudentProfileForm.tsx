import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useStudentProfileForm } from "../hooks/useStudentProfileForm";
import { FormInput } from "@/components/custom/FormInput";
import CollegeSelector from "./CollegeSelector";
import { UUID } from "crypto";

export function StudentProfileForm({ userId }: { userId: UUID }) {
  const { form, onSubmit, isLoading } = useStudentProfileForm(userId);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        {/* Full Name */}
        <FormInput
          control={form.control}
          name="fullName"
          label="Full Name"
          placeholder="Enter your full name as per university records."
        />

        {/* College */}
        <CollegeSelector control={form.control} />

        {/* Branch */}
        <FormInput
          control={form.control}
          name="branch"
          label="Branch"
          placeholder="Enter your branch of study."
        />

        {/* Pass out Year */}
        <FormField
          control={form.control}
          name="passout"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Passout Year</FormLabel>
              <FormControl>
                <Input
                  {...field}
                  type="number"
                  onChange={(e) => field.onChange(parseInt(e.target.value, 10))}
                  placeholder="Enter your expected year of graduation."
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        {/* Phone */}
        <FormInput
          control={form.control}
          name="phone"
          label="Phone Number"
          placeholder="Enter your contact number."
        />

        <div className="flex justify-between">
          <Button type="submit" disabled={isLoading}>
            {isLoading ? "Saving..." : "Save Profile"}
          </Button>
        </div>
      </form>
    </Form>
  );
}
