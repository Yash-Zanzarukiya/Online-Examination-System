import { CalendarIcon } from "lucide-react";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Input } from "@/components/ui/input";
import { UUID } from "crypto";
import { useExamScheduleForm } from "../hooks";
import { ScheduledExam } from "../types";
import { FormInput } from "@/components/custom/FormInput";
import CollegeSelector from "@/features/student/components/CollegeSelector";

interface ExamScheduleFormProps {
  examId: UUID;
  initialData?: ScheduledExam;
}

function ExamScheduleForm({ examId, initialData }: ExamScheduleFormProps) {
  const { form, onSubmit } = useExamScheduleForm(examId, initialData);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        {/* name */}
        <FormInput
          control={form.control}
          name="name"
          label="Exam Name"
          placeholder="Enter exam name"
        />
        <CollegeSelector control={form.control} />
        {/* Date and time */}
        <FormField
          control={form.control}
          name="startingAt"
          render={({ field }) => (
            <FormItem className="flex flex-col">
              <FormLabel>Exam Start Date and Time</FormLabel>
              <Popover>
                <PopoverTrigger asChild>
                  <FormControl>
                    <Button
                      variant={"outline"}
                      className={cn(
                        "w-[300px] justify-start text-left font-normal",
                        !field.value && "text-muted-foreground"
                      )}
                    >
                      <CalendarIcon className="mr-2 h-4 w-4" />
                      {field.value ? (
                        format(field.value, "PPP HH:mm")
                      ) : (
                        <span>Pick a date and time</span>
                      )}
                    </Button>
                  </FormControl>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                  <Calendar
                    mode="single"
                    selected={field.value}
                    onSelect={(date) => {
                      if (date) {
                        const currentDate = field.value || new Date();
                        date.setHours(currentDate.getHours());
                        date.setMinutes(currentDate.getMinutes());
                        field.onChange(date);
                      }
                    }}
                    disabled={(date) => date < new Date() || date < new Date("1900-01-01")}
                    initialFocus
                  />
                  <div className="p-3 border-t border-border">
                    <div className="flex items-center gap-2">
                      <Input
                        type="time"
                        onChange={(e) => {
                          const [hours, minutes] = e.target.value.split(":");
                          const newDate = new Date(field.value || new Date());
                          newDate.setHours(parseInt(hours));
                          newDate.setMinutes(parseInt(minutes));
                          field.onChange(newDate);
                        }}
                        value={field.value ? format(field.value, "HH:mm") : ""}
                        className="w-full"
                      />
                    </div>
                  </div>
                </PopoverContent>
              </Popover>
              <FormDescription>Select the start date and time for the exam.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Save Schedule</Button>
      </form>
    </Form>
  );
}

export default ExamScheduleForm;
