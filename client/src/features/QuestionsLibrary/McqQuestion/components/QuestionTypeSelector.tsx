import { Control } from "react-hook-form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { QuestionType } from "@/types/QuestionType";
import { Difficulty } from "@/types/Difficulty";
import { CategoryDTO } from "@/types/Category";
import { McqQuestionForm } from "@/features/Question/validators/mcq-validator";
import { useCategory } from "@/features/Question/hooks";
import { Input } from "@/components/ui/input";

interface QuestionTypeSelectorProps {
  control: Control<McqQuestionForm>;
}

const QuestionTypeSelector: React.FC<QuestionTypeSelectorProps> = ({ control }) => {
  const { categories } = useCategory();

  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
      <FormField
        control={control}
        name="question.type"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Question Type</FormLabel>
            <Select
              disabled
              onValueChange={field.onChange}
              defaultValue={field.value || QuestionType.MCQ}
            >
              <FormControl>
                <SelectTrigger>
                  <SelectValue placeholder="Select type" />
                </SelectTrigger>
              </FormControl>
              <SelectContent>
                {Object.values(QuestionType).map((type) => (
                  <SelectItem key={type} value={type}>
                    {type}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            <FormMessage />
          </FormItem>
        )}
      />

      {/* enable only if type is MCQ */}
      <FormField
        control={control}
        name="question.categoryId"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Question Category</FormLabel>
            {categories.length && (
              <Select
                onValueChange={field.onChange}
                value={field.value}
                disabled={control._formValues.question.type !== QuestionType.MCQ}
              >
                <FormControl>
                  <SelectTrigger>
                    <SelectValue placeholder="Select Category" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  {categories.map((category: CategoryDTO) => (
                    <SelectItem key={category.id} value={category.id}>
                      {category.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={control}
        name="question.difficulty"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Difficulty</FormLabel>
            <Select onValueChange={field.onChange} defaultValue={field.value}>
              <FormControl>
                <SelectTrigger>
                  <SelectValue placeholder="Select difficulty" />
                </SelectTrigger>
              </FormControl>
              <SelectContent>
                {Object.values(Difficulty).map((difficulty) => (
                  <SelectItem key={difficulty} value={difficulty}>
                    {difficulty}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={control}
        name="question.marks"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Marks</FormLabel>
            <FormControl>
              <Input {...field} type="number" placeholder="Enter marks" className="max-w-28" />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
    </div>
  );
};

export default QuestionTypeSelector;
