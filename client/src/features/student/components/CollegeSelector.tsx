import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useCollegeCrud } from "@/features/college/hooks/useCollegeCrud";
import { useEffect } from "react";

function CollegeSelector({ control }: { control: any }) {
  const { colleges, loadColleges, loading } = useCollegeCrud();

  useEffect(() => {
    loadColleges();
  }, [loadColleges]);

  return (
    <FormField
      control={control}
      name="collegeId"
      render={({ field }) => (
        <FormItem>
          <FormLabel>College</FormLabel>
          <Select onValueChange={field.onChange} defaultValue={field.value}>
            <FormControl>
              <SelectTrigger>
                <SelectValue placeholder="Select your college" />
              </SelectTrigger>
            </FormControl>
            <SelectContent>
              {loading && <SelectItem value="loading...">Loading...</SelectItem>}
              {colleges.map((college) => (
                <SelectItem key={college.id} value={college.id}>
                  {college.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
          <FormDescription>Select your college from the list.</FormDescription>
          <FormMessage />
        </FormItem>
      )}
    />
  );
}

export default CollegeSelector;
