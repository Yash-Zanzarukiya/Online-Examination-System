import { Button } from "@/components/ui/button";
import { QuestionTable } from "../components/LibraryQuestionTable";
import ImportButton from "../components/ImportButton";
import { useLibraryQuestions } from "../hooks";

export default function QuestionLibrary() {
  const { questions } = useLibraryQuestions();

  return (
    <div className="container mx-auto p-10 pt-5">
      <h1 className="text-2xl font-bold mb-3">Question Library</h1>
      <div className="flex justify-end gap-2">
        <ImportButton />
        <Button>Add Question</Button>
      </div>
      <QuestionTable data={questions} />
    </div>
  );
}
