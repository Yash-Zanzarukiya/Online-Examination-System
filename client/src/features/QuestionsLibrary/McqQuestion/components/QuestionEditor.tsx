import { Button } from "@/components/ui/button";
import { FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { Textarea } from "@/components/ui/textarea";
import { Image, Trash2, Upload, X } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { useState } from "react";
import { TypographyH4 } from "@/components/ui/TypographyH4";
import { UseFormReturn } from "react-hook-form";
import ConfirmDialog from "@/components/custom/ConfirmDialog";
import { McqQuestionForm } from "@/features/Question/validators/mcq-validator";

interface QuestionEditorProps {
  form: UseFormReturn<McqQuestionForm, any, undefined>;
  onDeleteQuestion: () => void;
  onUpdateQuestion: (data: McqQuestionForm) => void;
}

export default function QuestionEditor({
  form,
  onDeleteQuestion,
  onUpdateQuestion,
}: QuestionEditorProps) {
  const [localImagePreview, setLocalImagePreview] = useState<string | null>(null);

  const { register, setValue, watch, getValues } = form;

  watch(`question.imageFile`);
  const imageUrl = watch(`question.imageUrl`);

  const handleRemoveImage = () => {
    setValue(`question.imageUrl`, "");
    setValue(`question.imageFile`, undefined);
    setLocalImagePreview(null);
  };

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setValue(`question.imageFile`, file);
      setLocalImagePreview(URL.createObjectURL(file));
      setValue(`question.imageUrl`, "");
    }
  };

  const handleUpdate = () => onUpdateQuestion(getValues());

  return (
    <>
      <FormField
        control={form.control}
        name="question.questionText"
        render={({ field }) => (
          <FormItem className="mb-4">
            <FormLabel className="flex text-lg font-semibold mb-2">
              <TypographyH4>Question</TypographyH4>
              <div className="ml-auto flex space-x-2">
                {/* Image Upload Button */}
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
                        <Label htmlFor="questionImageUrl">Image URL</Label>
                        <Input
                          id="questionImageUrl"
                          placeholder="https://example.com/image.jpg"
                          {...register(`question.imageUrl`)}
                        />
                      </div>
                      <div className="space-y-2">
                        <Label htmlFor="questionLocalImage">Upload from local</Label>
                        <Input
                          id="questionLocalImage"
                          type="file"
                          accept="image/*"
                          onChange={handleImageUpload}
                        />
                      </div>
                    </div>
                  </PopoverContent>
                </Popover>

                {/* Update Question */}
                <Button variant="outline" size="icon" type="button" onClick={handleUpdate}>
                  <Upload className="h-4 w-4" />
                </Button>

                {/* Delete Question */}
                <ConfirmDialog onConfirm={onDeleteQuestion}>
                  <Button variant="outline" size="icon" type="button">
                    <Trash2 className="h-4 w-4" />
                  </Button>
                </ConfirmDialog>
              </div>
            </FormLabel>
            <FormControl>
              <div className="flex flex-col space-y-2">
                <Textarea
                  {...field}
                  placeholder="Enter your question here..."
                  className="min-h-[100px]"
                />
              </div>
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
      {/* Image Preview */}
      {(imageUrl || localImagePreview) && (
        <div className="mt-2 mb-4 relative">
          <img
            src={localImagePreview || imageUrl || ""}
            alt="Question image"
            className="w-auto max-h-36 rounded"
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
    </>
  );
}
