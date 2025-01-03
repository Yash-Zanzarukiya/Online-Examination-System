import { QuestionTable } from "../components/LibraryQuestionTable";
import ImportButton from "../components/ImportButton";
import { useLibraryQuestions } from "../hooks";
import { AddQuestionButton } from "../components";

export default function QuestionLibrary() {
  const { questions } = useLibraryQuestions();

  return (
    <div className="container mx-auto p-10 pt-5">
      <h1 className="text-2xl font-bold mb-3">Question Library</h1>
      <div className="flex justify-end gap-2">
        <ImportButton />
        <AddQuestionButton />
      </div>
      <QuestionTable data={questions} />
    </div>
  );
}
