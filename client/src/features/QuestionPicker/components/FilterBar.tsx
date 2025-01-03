import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { QuestionFilter } from "../../QuestionBuilder/types";
import { Difficulty } from "@/types/Difficulty";
import { QuestionType } from "@/types/QuestionType";
import { UUID } from "crypto";
import { useCategory } from "../../Question/hooks/useCategory";

interface FilterBarProps {
  onFilterChange: (filter: Partial<QuestionFilter>) => void;
  currentFilter: QuestionFilter;
}

export default function FilterBar({ onFilterChange, currentFilter }: FilterBarProps) {
  const { categories, isLoading } = useCategory();

  return (
    <div className="flex flex-wrap gap-4">
      <div className="flex-1 min-w-[200px]">
        <Label htmlFor="difficulty" className="mb-2 block">
          Difficulty
        </Label>
        <Select
          value={currentFilter.difficulty || "all"}
          onValueChange={(value) =>
            onFilterChange({
              difficulty: value === "all" ? undefined : (value as Difficulty),
            })
          }
        >
          <SelectTrigger id="difficulty" className="w-full">
            <SelectValue placeholder="All Difficulties" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">All Difficulties</SelectItem>
            {Object.values(Difficulty).map((difficulty) => (
              <SelectItem key={difficulty} value={difficulty}>
                {difficulty}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div className="flex-1 min-w-[200px]">
        <Label htmlFor="type" className="mb-2 block">
          Question Type
        </Label>
        <Select
          value={currentFilter.type || "all"}
          onValueChange={(value) =>
            onFilterChange({
              type: value === "all" ? undefined : (value as QuestionType),
            })
          }
        >
          <SelectTrigger id="type" className="w-full">
            <SelectValue placeholder="All Types" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">All Types</SelectItem>
            <SelectItem value={QuestionType.MCQ}>Multiple Choice</SelectItem>
            <SelectItem value={QuestionType.PROGRAMMING}>Programming</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="flex-1 min-w-[200px]">
        <Label htmlFor="category" className="mb-2 block">
          Category
        </Label>
        <Select
          value={currentFilter.categoryId || "all"}
          disabled={currentFilter.type === QuestionType.PROGRAMMING}
          onValueChange={(value) =>
            onFilterChange({
              categoryId: value === "all" ? undefined : (value as UUID),
            })
          }
        >
          <SelectTrigger id="category" className="w-full">
            <SelectValue placeholder="All Categories" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">All Categories</SelectItem>
            {isLoading && (
              <SelectItem value="loading" disabled>
                Loading...
              </SelectItem>
            )}
            {categories?.map((category) => (
              <SelectItem key={category.id} value={category.id}>
                {category.name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
    </div>
  );
}
