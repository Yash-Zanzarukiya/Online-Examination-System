import { Control, useFieldArray, useWatch } from "react-hook-form";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";
import { OptionCard } from ".";
import { TypographyH4 } from "@/components/ui/TypographyH4";
import { McqQuestionForm } from "@/features/Question/validators/mcq-validator";
import { useMemo } from "react";

interface OptionsListProps {
  control: Control<McqQuestionForm>;
  onAddOption: () => void;
  onDeleteOption: (index: number) => void;
  onUpdateOption: (index: number) => void;
}

export default function OptionsList({
  control,
  onAddOption,
  onDeleteOption,
  onUpdateOption,
}: OptionsListProps) {
  const { fields } = useFieldArray({
    control,
    name: "options",
  });

  const options = useWatch({
    control,
    name: "options",
  });

  const correctOptionIndex = useMemo<number>(
    () => options.findIndex((option) => option.isCorrect),
    [options]
  );

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
            index={index}
            control={control}
            onDelete={onDeleteOption}
            onUpdate={onUpdateOption}
            isCorrect={index === correctOptionIndex}
          />
        ))}
      </div>
    </div>
  );
}
