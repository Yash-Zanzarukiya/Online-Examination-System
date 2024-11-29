import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { getAllFullQuestionsThunk } from "../redux/questionThunks";
import { QuestionComponent } from "../components";

function AllQuestions() {
  const dispatch = useAppDispatch();

  const { questions } = useAppSelector(({ questions }) => questions);

  useEffect(() => {
    dispatch(getAllFullQuestionsThunk({}));
  }, []);

  return (
    <div className="container mx-auto p-6 max-w-5xl space-y-4">
      {questions?.map((fullQuestion) => (
        <QuestionComponent key={fullQuestion.question.id} initialData={fullQuestion} />
      ))}
    </div>
  );
}

export default AllQuestions;
