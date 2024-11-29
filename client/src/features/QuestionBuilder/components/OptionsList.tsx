import { Control, useFieldArray, useWatch } from "react-hook-form";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";
import { QuestionCreatePayload } from "../types";
import { OptionCard } from ".";
import { TypographyH4 } from "@/components/ui/TypographyH4";

interface OptionsListProps {
  control: Control<QuestionCreatePayload>;
  onAddOption: () => void;
  onDeleteOption: (index: number) => void;
}

export default function OptionsList({ control, onAddOption, onDeleteOption }: OptionsListProps) {
  const { fields } = useFieldArray({
    control,
    name: "options",
  });

  const options = useWatch({
    control,
    name: "options",
  });

  const correctOptionIndex = options.findIndex((option) => option.isCorrect);

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <TypographyH4>Options</TypographyH4>
        <Button onClick={onAddOption} type="button" variant="outline" className="">
          <PlusCircle className="h-4 w-4" />
          Add Option
        </Button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {fields.map((field, index) => (
          <OptionCard
            key={field.id || index}
            control={control}
            index={index}
            onDeleteOption={onDeleteOption}
            isCorrect={index === correctOptionIndex}
          />
        ))}
      </div>
    </div>
  );
}
