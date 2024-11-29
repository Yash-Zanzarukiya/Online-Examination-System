import { useState } from "react";
import { useFormContext } from "react-hook-form";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Image, Trash2, Upload, X } from "lucide-react";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { FormField, FormItem, FormControl, FormLabel, FormMessage } from "@/components/ui/form";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";

interface OptionCardProps {
  control: any;
  index: number;
  onDeleteOption: (index: number) => void;
  isCorrect: boolean;
}

export default function OptionCard({ control, index, onDeleteOption, isCorrect }: OptionCardProps) {
  const { register, setValue, watch } = useFormContext();

  const [localImagePreview, setLocalImagePreview] = useState<string | null>(null);

  watch(`options.${index}.imageFile`);
  const imageUrl = watch(`options.${index}.imageUrl`);

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setValue(`options.${index}.imageFile`, file);
      setLocalImagePreview(URL.createObjectURL(file));
    }
  };

  const handleRemoveImage = () => {
    setValue(`options.${index}.imageUrl`, "");
    setValue(`options.${index}.imageFile`, null);
    setLocalImagePreview(null);
  };

  return (
    <Card className="bg-muted/30">
      <CardContent className="p-4">
        <div className="flex items-center mb-2">
          {/* Choice Radio Buttons */}
          <FormField
            control={control}
            name="correctOptionIndex"
            render={({ field }) => (
              <FormItem className="flex items-center space-x-2 ">
                <FormControl>
                  <RadioGroup
                    onValueChange={(value) => {
                      field.onChange(parseInt(value));
                      setValue(
                        "options",
                        watch("options").map((opt: any, i: number) => ({
                          ...opt,
                          isCorrect: i === parseInt(value),
                        }))
                      );
                    }}
                    value={field.value?.toString()}
                    defaultValue={isCorrect ? index.toString() : undefined}
                  >
                    <RadioGroupItem
                      value={index.toString()}
                      id={`option-${index}`}
                      className="mt-2"
                    />
                  </RadioGroup>
                </FormControl>
                <FormLabel htmlFor={`option-${index}`} className="text-lg font-semibold mb-2">
                  Choice {index + 1}
                </FormLabel>
              </FormItem>
            )}
          />

          {/* Image Upload and Delete Buttons */}
          <div className="ml-auto flex space-x-2">
            <Popover>
              <PopoverTrigger asChild>
                <Button variant="outline" size="icon">
                  <Image className="h-4 w-4" />
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-80">
                <div className="space-y-2">
                  <h4 className="font-medium">Add Image</h4>
                  <div className="space-y-2">
                    <Label htmlFor={`imageUrl-${index}`}>Image URL</Label>
                    <Input
                      id={`imageUrl-${index}`}
                      placeholder="https://example.com/image.jpg"
                      {...register(`options.${index}.imageUrl`)}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor={`localImage-${index}`}>Upload from local</Label>
                    <Input
                      id={`localImage-${index}`}
                      type="file"
                      accept="image/*"
                      onChange={handleImageUpload}
                    />
                  </div>
                </div>
              </PopoverContent>
            </Popover>

            <Button variant="outline" size="icon" type="button" onClick={() => {}}>
              <Upload className="h-4 w-4" />
            </Button>

            <Button
              variant="outline"
              size="icon"
              className="ml-auto"
              onClick={() => onDeleteOption(index)}
              disabled={watch("options").length <= 2}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        </div>

        {/* Option Text Area */}
        <FormField
          control={control}
          name={`options.${index}.optionText`}
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Textarea {...field} placeholder={`Option ${index + 1}`} className="min-h-[60px]" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        {/* Image Preview */}
        {(imageUrl || localImagePreview) && (
          <div className="mt-2 relative">
            <img
              src={localImagePreview || imageUrl || ""}
              alt={`Choice ${index + 1} image`}
              className="max-w-full max-h-36 h-auto rounded"
            />
            <Button
              variant="destructive"
              size="icon"
              className="absolute top-2 right-2"
              onClick={handleRemoveImage}
            >
              <X className="h-4 w-4" />
            </Button>
          </div>
        )}
      </CardContent>
    </Card>
  );
}
