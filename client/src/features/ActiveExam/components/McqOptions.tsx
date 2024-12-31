import React from "react";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Label } from "@/components/ui/label";
import { UUID } from "crypto";
import { Option } from "../types";

interface MCQOptionsProps {
  options: Option[];
  selectedOptionId: UUID | null;
  questionId: UUID;
  handleAnswerChange: (questionId: UUID, selectedOptionId: UUID) => void;
}

const MCQOptions: React.FC<MCQOptionsProps> = React.memo(
  ({ options, selectedOptionId, questionId, handleAnswerChange }) => {
    return (
      <RadioGroup
        onValueChange={(value) => handleAnswerChange(questionId, value as UUID)}
        value={selectedOptionId || undefined}
      >
        {options.map((option, index) => (
          <div key={index} className="flex items-center space-x-2 mb-4">
            <RadioGroupItem value={option.id} id={`option-${index}`} />
            <Label htmlFor={`option-${index}`}>
              {option.optionText}
              {option.image && (
                <img
                  src={option.image}
                  alt="Option"
                  className="mt-2 max-w-full h-auto rounded-lg"
                />
              )}
            </Label>
          </div>
        ))}
      </RadioGroup>
    );
  }
);

export default MCQOptions;
