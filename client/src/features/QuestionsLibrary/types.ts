import { McqQuestion } from "../Question/types/mcq-types";
import { ProgrammingQuestion } from "../Question/types/programming-types";
import { Question } from "../Question/types/question-types";

export interface QuestionLibraryState {
  questions: Question[];
  mcqQuestion: McqQuestion | null;
  programmingQuestion: ProgrammingQuestion | null;
  isLoading: boolean;
}
