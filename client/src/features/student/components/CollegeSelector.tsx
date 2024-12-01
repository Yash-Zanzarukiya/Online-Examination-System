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
import { useColleges } from "@/features/college/hooks/useColleges";

function CollegeSelector({ control }: { control: any }) {
  const { colleges, isLoading } = useColleges();

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
              {isLoading && <SelectItem value="loading...">Loading...</SelectItem>}
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
