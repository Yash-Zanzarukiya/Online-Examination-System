import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { getMcqQuestionById } from "../../redux/mcqQuestionThunks";
import { UUID } from "crypto";
import { McqQuestionFormComponent } from "../components";

function CreateMcqQuestion() {
  const dispatch = useAppDispatch();
  const { questionId } = useParams();

  const { mcqQuestion } = useAppSelector(({ questionLibrary }) => questionLibrary);

  useEffect(() => {
    if (questionId) {
      dispatch(getMcqQuestionById(questionId as UUID));
    }
  }, [questionId, dispatch]);

  return (
    <div className="container mx-auto p-10 max-w-5xl space-y-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-primary">Multiple Choice Question</h1>
      </div>
      <p className="text-muted-foreground mb-6">Build Multiple Choice questions in no time.</p>
      <McqQuestionFormComponent initialData={mcqQuestion || undefined} />
    </div>
  );
}

export default CreateMcqQuestion;
